<script setup lang="ts">

import {onMounted, defineProps, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
    postId: {
        type: [Number, String],
        required: true,
    }
})

const post = ref({
    id: 0,
    title: "",
    content: "",
});

const router = useRouter();
const moveToEdit = () => {
    router.push({name: "edit"})
}

onMounted(() => {
    axios.get(`/api/posts/${props.postId}`).then(res => {
        post.value = res.data;
    });
});
</script>

<template>
  <h2>{{post.title}}</h2>
  <div>{{post.content}}</div>
  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>

<style scoped>

</style>