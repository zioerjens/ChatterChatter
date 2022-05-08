import {User} from "./User";

export interface MessageDTO {
  id?: number;
  time?: string;
  sender?: User;
  subjectId: number;
  content: string;
}
