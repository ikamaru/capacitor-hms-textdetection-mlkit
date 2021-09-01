import { WebPlugin } from '@capacitor/core';

import type { TextDetectionMlKitPlugin } from './definitions';

export class TextDetectionMlKitWeb extends WebPlugin implements TextDetectionMlKitPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
