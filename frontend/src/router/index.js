import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BookGrid.vue'),
    },
    {
      path: '/bookDetailInfos',
      component: () => import('../components/BookDetailInfoView.vue'),
    },
    {
      path: '/bookLists',
      component: () => import('../components/BookListView.vue'),
    },
    {
      path: '/writers',
      component: () => import('../components/ui/WriterGrid.vue'),
    },
    {
      path: '/publicationApprovedManagements',
      component: () => import('../components/PublicationApprovedManagementView.vue'),
    },
    {
      path: '/users',
      component: () => import('../components/ui/UserGrid.vue'),
    },
    {
      path: '/userInfoLists',
      component: () => import('../components/UserInfoListView.vue'),
    },
    {
      path: '/subscriptions',
      component: () => import('../components/ui/SubscriptionGrid.vue'),
    },
    {
      path: '/subscriptionStatusChecks',
      component: () => import('../components/SubscriptionStatusCheckView.vue'),
    },
    {
      path: '/bestSellers',
      component: () => import('../components/ui/BestSellerGrid.vue'),
    },
    {
      path: '/bestSellerLists',
      component: () => import('../components/BestSellerListView.vue'),
    },
    {
      path: '/openAis',
      component: () => import('../components/ui/OpenAiGrid.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/pointInfoChecks',
      component: () => import('../components/PointInfoCheckView.vue'),
    },
  ],
})

export default router;
