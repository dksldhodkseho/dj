<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              {{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
      {
          text: "도서",
          link: "/books",
      },
      {
          text: "도서 상세정보",
          link: "/bookDetailInfos",
      },
      {
          text: "도서목록",
          link: "/bookLists",
      },
      {
          text: "작가",
          link: "/writers",
      },
      {
          text: "출간 승인 관리",
          link: "/publicationApprovedManagements",
      },
      {
          text: "사용자",
          link: "/users",
      },
      {
          text: "회원 정보 목록",
          link: "/userInfoLists",
      },
      {
          text: "구독",
          link: "/subscriptions",
      },
      {
          text: "구독 현황 조회",
          link: "/subscriptionStatusChecks",
      },
      {
          text: "베스트셀러",
          link: "/bestSellers",
      },
      {
          text: "베스트셀러 목록",
          link: "/bestSellerLists",
      },
      {
          text: "OpenAi",
          link: "/openAis",
      },
      {
          text: "포인트",
          link: "/points",
      },
      {
          text: "포인트 정보 조회",
          link: "/pointInfoChecks",
      },
    ],
  }),
};
</script>
