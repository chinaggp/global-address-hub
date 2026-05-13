<template>
  <section id="faq" class="container-page py-14">
    <div class="max-w-3xl">
      <h2 class="text-2xl font-bold text-brand-ink">{{ title }}</h2>
      <p class="mt-3 text-slate-600">
        {{ $t('faq_section.copy') }}
      </p>
    </div>
    <div class="mt-8 grid gap-4 md:grid-cols-2">
      <article v-for="item in items" :key="item.question" class="card p-5">
        <h3 class="text-base font-semibold text-brand-ink">{{ item.question }}</h3>
        <p class="mt-2 text-sm leading-6 text-slate-600">{{ item.answer }}</p>
      </article>
    </div>
  </section>
</template>

<script setup lang="ts">
import type { FaqItem } from '~/types/address'

withDefaults(
  defineProps<{
    title?: string
    items: FaqItem[]
  }>(),
  {
    title: 'FAQ'
  }
)
</script>
