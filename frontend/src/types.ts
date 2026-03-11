export interface CourseSummary {
  id: number;
  title: string;
  level: string;
  estimatedHours: number | null;
  published: boolean;
}

export interface Lesson {
  id: number;
  title: string;
  summary?: string;
  contentUrl?: string;
  sequenceNumber?: number;
  durationMinutes?: number;
}

export interface Material {
  id: number;
  title: string;
  description?: string;
  resourceUrl?: string;
  type: string;
}

export interface CourseDetail {
  id: number;
  title: string;
  description?: string;
  level: string;
  estimatedHours?: number;
  published: boolean;
  lessons: Lesson[];
  materials: Material[];
}

export interface ThreadMessage {
  id: number;
  content: string;
  sentAt: string;
  sender?: {
    name?: string;
  };
}

export interface Thread {
  id: number;
  topic: string;
  createdAt: string;
  creator?: {
    name?: string;
  };
  messages?: ThreadMessage[];
}

export interface ProgressReport {
  learnerId: number;
  learnerName: string;
  lessonsCompleted: number;
  lessonSummary: Record<string, number>;
}
