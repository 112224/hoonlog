<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const title = ref("");
const content = ref("");

const write = function () {
    console.log(title.value +"  " + content.value);
    axios.post("/api/posts"
        , {
            "title": title.value,
            "content": content.value
        }
    ).then(() => {
        router.replace({name: "home"});
    });
}

</script>

<template>
    <div class="mt-5">
      <el-input type="text" v-model="title" placeholder="제목을 입력해주세요."/>
    </div>
    <div class="mt-2">
        <el-input type="textarea" v-model="content" rows="15"/>
    </div>
    <div class="mt-2">
        <el-button type="primary" @click="write">글 작성완료</el-button>
    </div>
</template>

<style scoped>

</style>