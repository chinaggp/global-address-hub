<template>
  <section class="border-y border-brand-border bg-brand-surface">
    <div class="container-page py-14">
      <div class="max-w-3xl">
        <h2 class="text-2xl font-bold text-brand-ink">{{ title }}</h2>
        <div class="mt-4 space-y-4 text-base leading-7 text-slate-600">
          <p v-for="paragraph in paragraphs" :key="paragraph">{{ paragraph }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title: string
  paragraphs: string[]
}>()
</script>
