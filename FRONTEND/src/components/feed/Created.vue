<template>
  <v-container fluid>
    <v-row dense>
      <v-col
        v-for="challenge in challenges"
        :key="challenge.challenge_no"
        :cols="4"
      >
        <v-hover>
          <template v-slot:default="{ hover }">
            <v-card
              class="mx-auto img-frame"
              max-width="344"
              @click="movePage(challenge)"
            >
              <v-img
                v-if="challenge.video"
                min-height="190"
                max-height="344"
                :src="
                  'http://d3iu4sf4n4i2qf.cloudfront.net/' +
                  challenge.file_path +
                  '/thumbnail/' +
                  challenge.file_savedname +
                  '.png'
                "
              >
                <v-icon icon class="play-btn">mdi-play</v-icon>
              </v-img>
              <v-img
                v-else
                min-height="190"
                max-height="344"
                :src="
                  'http://d3iu4sf4n4i2qf.cloudfront.net/' +
                  challenge.file_path +
                  '/' +
                  challenge.file_savedname
                "
              >
              </v-img>
              <v-fade-transition>
                <v-overlay v-if="hover" absolute color="#2E2E2E">
                  <div class="info-wrapper">
                    <div class="challenge-info">
                      <v-icon icon class="icon hashtag-icon">mdi-pound</v-icon
                      >{{ challenge.challenge_title }}
                    </div>
                    <div class="participated-info">
                      <v-icon icon class="icon challenger-icon"
                        >mdi-account-plus</v-icon
                      >{{ challenge.post_cnt
                      }}<v-icon icon class="icon bookmark-icon"
                        >mdi-bookmark</v-icon
                      >{{ challenge.subscription_cnt }}
                    </div>
                  </div>
                </v-overlay>
              </v-fade-transition>
            </v-card>
          </template>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getUserMadeChallege } from "@/api/feed.js";

export default {
  name: "Created",
  props: {
    who_no: Number,
  },
  data: () => ({
    challenges: [],
  }),
  created() {
    // 유저가 만든 포스트 목록
    getUserMadeChallege(this.who_no, (response) => {
      this.challenges = response.data.data;
    });
  },
  methods: {
    movePage(val) {
      var path = "/challenge/" + val.challenge_no + "/#" + val.post_no;
      this.$router.push(path);
    },
  },
};
</script>

<style scoped>
.img-frame {
  cursor: pointer;
}
.info-wrapper {
  text-align: center;
}
.play-btn {
  float: right;
  width: 40px;
  color: #fff;
  margin-top: 8px;
}
.challenge-info {
  font-size: 20px;
  margin-bottom: 4px;
}
.participated-info {
  font-size: 18px;
}
.icon {
  margin-top: -2px;
  margin-left: 6px;
  margin-right: 4px;
}
.hashtag-icon {
  margin-top: -5px;
}
.challenger-icon {
  margin-right: 9px;
}
</style>
