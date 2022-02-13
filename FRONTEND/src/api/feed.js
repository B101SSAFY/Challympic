import { apiInstance } from './index.js';

const api = apiInstance();

function checkFollow(user_no, follow_follower_no, success, fail) {
    api.defaults.headers["Authorization"] = localStorage.getItem("Authorization");
  api.get(`/challympic/user/${user_no}/follow/${follow_follower_no}`).then(success).catch(fail);
}

function setFollow(user_no, follow_follower_no, success, fail){
    api.defaults.headers["Authorization"] = localStorage.getItem("Authorization");
    api.post(`/challympic/user/${user_no}/follow`, {follow_follower_no}).then(success).catch(fail);
}

function getFollowCnt(user_no, success, fail){
  api.get(`/challympic/${user_no}/follow`).then(success).catch(fail);
}

function getFollowerList(user_no, success, fail){
    api.get(`/challympic/${user_no}/follower`).then(success).catch(fail);
}

function getFollowingList(user_no, success, fail){
    api.get(`/challympic/${user_no}/following`).then(success).catch(fail);
}

function getUserMadePost(user_no, success, fail){
  api.get(`/challympic/post/${user_no}`).then(success).catch(fail);
}


export { checkFollow, setFollow , getFollowCnt, getFollowerList, getFollowingList, getUserMadePost };