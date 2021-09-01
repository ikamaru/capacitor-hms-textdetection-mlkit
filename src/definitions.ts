export interface TextDetectionMlKitPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
