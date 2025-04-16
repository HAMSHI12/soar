export interface ActionResponse {
  id: number;
  eventId: string;
  eventType: string;
  actionTaken: string;
  details: string;
  sourceIp: string;
  port: number;
  protocol: string;
  cybersecurityDevice: string;
  severity: string;
}