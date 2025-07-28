import React from 'react'
import './css/Post.css'
import { Avatar } from '@mui/material'
import Modal from 'react-responsive-modal';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import CloseIcon from '@mui/icons-material/Close';
import ReactTimeAgo from 'react-time-ago'
import axios from 'axios'
import ReactHtmlParser from 'html-react-parser'
import { useSelector } from 'react-redux';
import { selectUser } from '../feature/userSlice';

function LastSeen({ date }) {
    return (
        <div>
            <ReactTimeAgo date={new Date(date)} locale="en-US"/>
        </div>
    );
}
function Post({ post }) {
    const [isModalOpen, setIsModalOpen] = React.useState(false);
    const [answer, setAnswer] = React.useState("");
    const Close = <CloseIcon />;
    const user = useSelector(selectUser)

    const handleQuill = (value) => {
        setAnswer(value);
    };
    // console.log(answer);

    const handleSubmit = async () => {
        if (post?.qId && answer !== "") {
            const config = {
                headers: {
                    "Content-Type": "application/json",
                },
            };
            const body = {
                content: answer,
                question: {id: post?.qId},
                answeredBy: {id: user.uid}
            };
            await axios
                .post("http://localhost:8080/answers", body, {...config, withCredentials: true})
                .then((res) => {
                    console.log(res.data);
                    alert("Answer added successfully");
                    setIsModalOpen(false);
                    window.location.href = "/";
                })
                .catch((e) => {
                    console.log(e);
                });
        }
    };
    return (
        <div className="post">
            <div className="post-info">
                {/* <Avatar src = {post?.user?.photo}/> */}
                <h4>{post?.askedBy}</h4>

                <small>
                    <LastSeen date={post?.askedAt} />
                </small>
            </div>
            <div className="post-body">
                <div className="post-question">
                    <p>{post?.content}</p>
                    <button
                        onClick={() => {
                            setIsModalOpen(true);
                        }}
                        className="post-btn-answer"
                    >
                        Answer
                    </button>
                    <Modal
                        open={isModalOpen}
                        closeIcon={Close}
                        onClose={() => setIsModalOpen(false)}
                        closeOnEsc
                        center
                        closeOnOverlayClick={false}
                        styles={{
                            overlay: {
                                height: "auto",
                            },
                        }}
                    >
                        <div className="modal-question">
                            <h3>{post?.content}</h3>
                            <p>
                                asked by {post?.askedBy} on{" "}
                                { <span className="name">
                                   {new Date(post?.askedAt).toLocaleString()}
                                </span> }
                            </p>
                        </div>
                        <div className="modal-answer">
                            <ReactQuill
                                value={answer}
                                onChange={handleQuill}
                                placeholder="Write your answer"
                            />
                        </div>
                        <div className="modal-button">
                            <button className="cancel" onClick={() => setIsModalOpen(false)}>
                                Cancel
                            </button>
                            <button onClick={handleSubmit} type="submit" className="add">
                                Add Answer
                            </button>
                        </div>
                    </Modal>
                </div>
                {/* {post.questionUrl !== "" && <img src={post.questionUrl} alt="url" />} */}
            </div>
            <p
                style={{
                    color: "rgba(0,0,0,0.5)",
                    fontSize: "12px",
                    fontWeight: "bold",
                    margin: "10px 0",
                }}
            >
                {post?.answers.length} Answer(s)
            </p>

            <div
                style={{
                    margin: "5px 0px 0px 0px ",
                    padding: "5px 0px 0px 20px",
                    borderTop: "1px solid lightgray",
                }}
                className="post-answer"
            >
                {post?.answers?.map((_a) => (
                    <>
                        <div
                            style={{
                                display: "flex",
                                flexDirection: "column",
                                width: "100%",
                                padding: "10px 5px",
                                borderTop: "1px solid lightgray",
                            }}
                            className="post-answer-container"
                        >
                            <div
                                style={{
                                    display: "flex",
                                    alignItems: "center",
                                    marginBottom: "10px",
                                    fontSize: "12px",
                                    fontWeight: 600,
                                    color: "#888",
                                }}
                                className="post-answered"
                            >
                                {/* <Avatar src = {_a?.user?.photo}/> */}
                                <div
                                    style={{
                                        margin: "0px 10px",
                                    }}
                                    className="post-info"
                                >
                                    <p>{_a?.answeredBy}</p>
                                    <small>
                                        <LastSeen date={_a?.answeredAt} />
                                    </small>
                                </div>
                            </div>
                            <div className="post-answer">{ReactHtmlParser(_a?.content)}</div>
                        </div>
                    </>
                ))}
            </div>
        </div>
    );
}

export default Post