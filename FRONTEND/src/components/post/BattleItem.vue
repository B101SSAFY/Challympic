<template>
  <div>
    <!-- 게이지바 -->
    <div v-if="postList.length > 1">
      <div class="gauge-items my-3">
        <strong class="ml-auto nickname">
          <v-icon v-if="postList[1].likeCnt < postList[0].likeCnt"
            >mdi-crown</v-icon
          >{{ postList[0].user_nickname }}</strong
        >
        <v-progress-linear
          background-color="#3396F4"
          color="pink lighten-1"
          :value="value"
          height="17px"
        >
          <strong>{{ postList[0].likeCnt }}</strong>
          <strong>{{ postList[1].likeCnt }}</strong>
        </v-progress-linear>

        <strong class="mr-auto nickname">
          {{ postList[1].user_nickname }}
          <v-icon v-if="postList[1].likeCnt > postList[0].likeCnt"
            >mdi-crown</v-icon
          ></strong
        >
      </div>
      <v-row>
        <v-col cols="12" md="6">
          <post-item :post="postList[0]" :type="type" :user="user" />
        </v-col>
        <v-col cols="12" md="6">
          <post-item :post="postList[1]" :type="type" :user="user" />
        </v-col>
      </v-row>
    </div>
    <div v-else>
      <v-chip class="info-text">아직 참여하지 않은 챌린저가 있습니다.</v-chip>
      <post-item :post="postList[0]" :type="type" :user="user" />
    </div>
  </div>
</template>

<script>
import PostItem from "@/components/post/PostItem.vue";
export default {
  name: "BattleItem",
  components: { PostItem },
  props: {
    type: String,
    postList: Array,
    user: Object,
  },

  computed: {
    value() {
      let all = this.postList[0].likeCnt + this.postList[1].likeCnt;
      let post1 = this.postList[0].likeCnt;
      return (post1 / all) * 100;
    },
  },
};
</script>

<style scoped>
.gauge-items {
  display: flex;
  align-items: center;
}
.nickname {
  font-size: 20px;
}
.v-progress-linear {
  display: block;
  width: 30%;
  margin: 0 auto;
  border-radius: 10px;
}
::v-deep .v-progress-linear__content {
  justify-content: space-between;
  padding: 0 0.5rem;
  color: white;
}
.info-text {
  margin-top: 0.5rem;
}
</style>
