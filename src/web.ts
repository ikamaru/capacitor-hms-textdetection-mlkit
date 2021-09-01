import { WebPlugin } from '@capacitor/core';

import type { TextDetectionMlKitPlugin } from './definitions';

export class TextDetectionMlKitWeb extends WebPlugin implements TextDetectionMlKitPlugin {
  getTextFromImage(): Promise<{ result: any; }> {
    throw new Error('Method not implemented for web.');
  }
}
