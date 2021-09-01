import { registerPlugin } from '@capacitor/core';

import type { TextDetectionMlKitPlugin } from './definitions';

const TextDetectionMlKit = registerPlugin<TextDetectionMlKitPlugin>('TextDetectionMlKit', {
  web: () => import('./web').then(m => new m.TextDetectionMlKitWeb()),
});

export * from './definitions';
export { TextDetectionMlKit };
