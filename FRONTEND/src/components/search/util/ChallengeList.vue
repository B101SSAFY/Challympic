<template>
  <div class="holder">
    <!-- Post List for each ChallengeList -->
    <v-carousel-item v-for="(item, idx) in post" :key="item.post_no">
      <div>
        <!-- v-if="isVideo === 'VIDEO'"-->
        <!--
				<video-component
					:VideoUrl="`https://d3iu4sf4n4i2qf.cloudfront.net/${item.file_path}/video/${item.file_savedame}.m3u8`"
				/>
						<v-img
							v-else
							:src="`https://d3iu4sf4n4i2qf.cloudfront.net/${item.file_path}/${item.file_savedame}`"
							class="video-player-box"
							height="280!important"
						/>
						--></div>

      <!-- 포스트 정보 -->
      <div class="bar">
        <v-card-title>
          <router-link
            :to="{ path: `/feed/${item.user_no}` }"
            style="text-decoration: none; color: inherit"
          >
            <h3>
              {{ item.user_nickname }}
            </h3>
          </router-link>
          <v-spacer />
        </v-card-title>

        <v-card-subtitle>
          <!--좋아요 {{ item.post_likes }} 개 댓글 {{ item.post_comments }}개
					-->
        </v-card-subtitle>
      </div>
      <!-- 좋아요-->
      <div class="bar-heart">
        <v-btn @click="pushLike(item.post_no, idx)" icon>
          <!-- <v-icon :class="{ 'show-btns': hover }" :color="transparent">
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시
								:class="{ 'show-btns': hover }"
								-->
          <v-icon :color="item.post_like ? 'red' : 'grey lighten-3'" size="32">
            mdi-heart-outline
          </v-icon>
        </v-btn>
      </div>
    </v-carousel-item>
  </div>
</template>

<script>
//	import VideoComponent from "./VideoComponent.vue";
// import { list } from "@/api/search.js";

export default {
  //		components: { VideoComponent },
  name: "ChallengeList",
  props: {
    post: Object,
    isVideo: String,
  },
  data() {
    return {
      overlay: false,
      playing: false,
      temp: [],
      loaded: false,
      challenges: {
        user_no: this.$store.state.userStore.userInfo.user_no,
        challenge_no: this.challengeNo,
      },
    };
  },
  methods: {
    pushLike(postid, arrIdx) {
      //arrInx는 화면 바로 바꾸는용도
      //postid로 좋아요 요청
      this.post[arrIdx].post_like = !this.post[arrIdx].post_like;
      if (this.post[arrIdx].post_like) {
        this.post[arrIdx].post_likes++;
      } else {
        this.post[arrIdx].post_likes--;
      }
    },
    goChallenge() {
      // alert(
      //   "챌린지 상세 페이지로 이동 where challenge_no:" +
      //     this.challenge.challenge_no
      // );
      const path = "/challenge/" + this.challenge.challenge_no;
      this.$router.push(path);
    },
    pushSubscribe() {
      /*alert(this.challenge.challenge_subscribe);*/
      //해당 챌린지 challenge.challenge_no로 구독 취소나 좋아요 ㅇ ㅛ청 보내기
      this.challenge.challenge_subscribe = !this.challenge.challenge_subscribe;
    },
  },
};
</script>

<style>
video {
  object-fit: fill;
}
.bar {
  position: absolute;
  top: 1%;
  left: 0;
}
.bar-heart {
  position: absolute;
  top: 7%;
  right: 5%;
}
.title-block {
  display: inline-block;
  cursor: pointer;
}
</style>
