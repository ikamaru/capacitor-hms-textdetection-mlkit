export interface TextDetectionMlKitPlugin {
  getTextFromImage(): Promise<{ result: any }>;
}