<template>
  <button :class="buttonClass" type="button" :disabled="!text" @click="copy">
    {{ statusText }}
  </button>
</template>

<script setup lang="ts">
const props = defineProps<{
  text?: string
  label?: string
  variant?: 'default' | 'success'
}>()

const copied = ref(false)
const failed = ref(false)
const { t } = useI18n()
let resetTimer: number | undefined

const statusText = computed(() => {
  if (copied.value) {
    return t('copy.copied')
  }
  if (failed.value) {
    return t('copy.failed')
  }
  return props.label || t('copy.default')
})

const buttonClass = computed(() => (props.variant === 'success' ? 'btn-success' : 'btn-secondary'))

function resetStatusSoon() {
  if (resetTimer) {
    window.clearTimeout(resetTimer)
  }

  resetTimer = window.setTimeout(() => {
    copied.value = false
    failed.value = false
  }, 1600)
}

async function copy() {
  if (!props.text) {
    return
  }

  if (!import.meta.client || !navigator.clipboard?.writeText) {
    copied.value = false
    failed.value = true
    resetStatusSoon()
    return
  }

  try {
    await navigator.clipboard.writeText(props.text)
    failed.value = false
    copied.value = true
  } catch {
    copied.value = false
    failed.value = true
  }

  resetStatusSoon()
}
</script>
