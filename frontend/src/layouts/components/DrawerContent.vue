<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '도서' }" />
        <VerticalNavLink
            :item="{
                title: '도서',
                to: '/books',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서 상세정보',
                to: '/bookDetailInfos',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '도서목록',
                to: '/bookLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '작가' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/writers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '출간 승인 관리',
                to: '/publicationApprovedManagements',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '사용자' }" />
        <VerticalNavLink
            :item="{
                title: '사용자',
                to: '/users',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '회원 정보 목록',
                to: '/userInfoLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독' }" />
        <VerticalNavLink
            :item="{
                title: '구독',
                to: '/subscriptions',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독 현황 조회',
                to: '/subscriptionStatusChecks',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '베스트셀러' }" />
        <VerticalNavLink
            :item="{
                title: '베스트셀러',
                to: '/bestSellers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '베스트셀러 목록',
                to: '/bestSellerLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Openai' }" />
        <VerticalNavLink
            :item="{
                title: 'OpenAi',
                to: '/openAis',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트 정보 조회',
                to: '/pointInfoChecks',
            }"
        />
    </ul>
</template>
