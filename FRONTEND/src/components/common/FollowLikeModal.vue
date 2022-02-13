<template>
  <v-dialog v-model="dialog" persistent scrollable max-width="400px">
    <v-card>
      <v-card-title class="lighten-2 card-title-align">
        <span v-if="this.type === 'follower'">팔로워</span>
        <span v-else-if="this.type === 'following'">팔로잉</span>
        <span v-else>좋아요</span>
        <v-btn class="cancel-btn" absolute top right icon @click="dialog = false; $emit('close-dialog')"> <v-icon>mdi-close</v-icon> </v-btn>
      </v-card-title>

      <v-divider />

      <div>
        <v-list class="overflow-y-auto">
          <v-list-item v-for="user in users" :key="user.user_no">
              <img v-if="user.user_title" class="medal-icon" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/>
            <v-list-item-avatar class="user-image">
              <v-img v-if="user.file_savedname" :alt="`${user.user_nickname} avatar`" :src="`https://d384sk7z91xokb.cloudfront.net/${user.file_path}/${user.file_savedname}`"></v-img>
              <v-img v-else :alt="`${user.user_nickname} avatar`" src="../../assets/profile.png"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <!-- 배지.. -->
              <!-- <v-img v-if="user.title" max-width="20" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/> -->
              <v-list-item-subtitle
                v-text="user.user_title"
                src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
              ></v-list-item-subtitle>
              <v-list-item-title v-text="user.user_nickname"></v-list-item-title>
            </v-list-item-content>

            <v-list-item-icon>
              <v-btn
                v-if="user.isFollowing"
                @click="follow(user.user_no)"
                color="#3396F4"
                class="white--text rounded-xl"
                small
              >
                팔로우
              </v-btn>
              <v-btn
                v-else
                @click="follow(user.user_no)"
                color="#3396F4"
                class="rounded-xl"
                small
                outlined
              >
                팔로잉
              </v-btn>
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "FollowLikeModal",
  props: {
    type: String,
    users: Array,
  },
  data() {
    return {
      dialog: true,
    };
  },
  methods: {
    follow(userNo) {
      console.log(userNo);
      // 팔로우 API 요청 보내기
      // 해당 유저에 대한 isFollowing 값 변경
    },
  },
};
</script>

<style scoped>
.card-title-align {
  display: flex;
  justify-content: center;
}
.cancel-btn {
  margin-top: -4px;
  margin-right: -4px;
}
.cancel-icon {
  width: 16px;
}
.follow-btn {
  color: #fff;
}
.user-image {
  margin-right: 16px;
}
.medal-icon {
  position: absolute;
  top: 0.5rem;
  left: 2.3rem;
  width: 20px;
  height: 20px;
  z-index: 1;
}
</style>