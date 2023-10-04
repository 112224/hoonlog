<script setup lang="ts">
import {defineProps, ref} from "vue";
import axios from "axios";
import router from "@/router";



const post = ref({
    id: 0,
    title: "",
    content: "",
});

const props = defineProps({
    postId: {
        type: [Number, String],
        required: true,
    }
})

const edit = () => {
    axios.patch(`/api/posts/${props.postId}`, post.value)
        .then(() => {
            router.replace({name: "home"})
        });
};

axios.get(`/api/posts/${props.postId}`).then(res => {
    post.value = res.data;
    console.log(post.value)
});


</script>

<template>
    <div class="mt-5">
        <el-input type="text" v-model="post.title" placeholder="제목을 입력해주세요."/>
    </div>
    <div class="mt-2">
        <el-input type="textarea" v-model="post.content" rows="15"/>
    </div>
    <div class="mt-2">
        <el-button type="warning" @click="edit">수정완료</el-button>
    </div>
</template>

<style scoped>

</style>