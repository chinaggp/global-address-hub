<template>
  <aside class="rounded-lg border border-amber-200 bg-amber-50 px-5 py-4">
    <p class="text-sm font-medium leading-6 text-amber-900">
      <span class="mr-1.5 inline-block text-base">⚠️</span>
      {{ text || $t('disclaimer_banner.default') }}
    </p>
  </aside>
</template>

<script setup lang="ts">
defineProps<{
  text?: string
}>()
</script>
