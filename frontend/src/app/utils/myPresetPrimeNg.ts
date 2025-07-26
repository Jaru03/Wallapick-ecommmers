import { definePreset } from '@primeuix/themes';
import Aura from '@primeuix/themes/aura';

export const MyPreset = definePreset(Aura, {
  semantic: {
    primary: {
      50: '#fff3e8', // muy claro, casi blanco con naranja
      100: '#ffd4b8',
      200: '#ffb989',
      300: '#ff9d59',
      400: '#ff8531',
      500: '#ff6b1f', // el naranja base que diste
      600: '#e65f1a',
      700: '#bf5015',
      800: '#993e10',
      900: '#732d0b',
      950: '#4d1b05', // muy oscuro
    },
  },
});
