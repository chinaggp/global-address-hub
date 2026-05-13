<template>
  <label class="block">
    <span class="text-sm font-semibold text-brand-ink">{{ label }}</span>
    <select
      class="mt-3 w-full rounded-lg border border-brand-border bg-white px-4 py-3 text-base text-brand-ink focus:border-brand-blue focus:outline-none focus:ring-2 focus:ring-blue-100 disabled:bg-slate-50"
      :disabled="loading || regions.length === 0"
      :value="modelValue"
      @change="onChange"
    >
      <option value="">{{ $t('generator.any_region') }}</option>
      <option v-for="region in regions" :key="region.code" :value="region.code">
        {{ region.name }}
      </option>
    </select>
  </label>
</template>

<script setup lang="ts">
import type { RegionOption } from '~/types/address'

withDefaults(
  defineProps<{
    label?: string
    loading?: boolean
    regions: RegionOption[]
    modelValue: string
  }>(),
  {
    label: 'Region',
    loading: false
  }
)

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

function onChange(event: Event) {
  emit('update:modelValue', (event.target as HTMLSelectElement).value)
}
</script>
