<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";

const posts = ref([]);
axios.get(
    "/api/posts"
).then(response => {
    response.data.forEach(r=>{
        posts.value.push(r);
    });
})
</script>

<template>
    <ul>
        <li v-for="post in posts" :key="post.id">
            <div>
                {{post.title}}
            </div>
            <div>
                {{post.content}}
            </div>
        </li>
    </ul>
</template>

<style scoped>
li {
    margin-top: 1rem;
}
li:last-child{
    margin-bottom: 0;
}
</style>
