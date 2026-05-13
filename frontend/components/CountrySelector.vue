<template>
  <label class="block">
    <span class="text-sm font-semibold text-brand-ink">{{ label }}</span>
    <select
      class="mt-3 w-full rounded-lg border border-brand-border bg-white px-4 py-3 text-base text-brand-ink focus:border-brand-blue focus:outline-none focus:ring-2 focus:ring-blue-100"
      :value="modelValue"
      @change="onChange"
    >
      <option v-for="country in countries" :key="country.code" :value="country.code">
        {{ flagMap[country.code] }} {{ country.name }}
      </option>
    </select>
  </label>
</template>

<script setup lang="ts">
import type { CountryOption } from '~/types/address'

defineProps<{
  countries: CountryOption[]
  modelValue: string
  label?: string
}>()

const flagMap: Record<string, string> = {
  US: '🇺🇸',
  JP: '🇯🇵',
  UK: '🇬🇧',
  CA: '🇨🇦',
  AU: '🇦🇺',
  TR: '🇹🇷',
  NG: '🇳🇬'
}

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

function onChange(event: Event) {
  emit('update:modelValue', (event.target as HTMLSelectElement).value)
}
</script>
