import { FormEvent, useEffect, useMemo, useState } from "react";
import { api } from "./api/client";
import { CourseDetail, CourseSummary, ProgressReport, Thread } from "./types";

type AuthPayload = {
  id: number;
  name: string;
  role: string;
};

export default function App() {
  const [courses, setCourses] = useState<CourseSummary[]>([]);
  const [selectedCourse, setSelectedCourse] = useState<CourseDetail | null>(null);
  const [threads, setThreads] = useState<Thread[]>([]);
  const [progress, setProgress] = useState<ProgressReport | null>(null);
  const [status, setStatus] = useState("");
  const [auth, setAuth] = useState<AuthPayload | null>(null);
  const [loginEmail, setLoginEmail] = useState("student@example.com");
  const [loginPassword, setLoginPassword] = useState("password");
  const [threadTopic, setThreadTopic] = useState("");
  const [threadMessage, setThreadMessage] = useState("");
  const [selectedThreadId, setSelectedThreadId] = useState<number | null>(null);

  const headers = useMemo(
    () => ({
      "X-Actor-Id": auth?.id ?? 0,
    }),
    [auth]
  );

  useEffect(() => {
    loadCourses();
    loadThreads();
  }, []);

  useEffect(() => {
    if (selectedCourse?.id) {
      refreshCourseDetail(selectedCourse.id);
    }
  }, [selectedCourse?.id]);

  async function loadCourses() {
    try {
      const { data } = await api.get<CourseSummary[]>("/api/courses");
      setCourses(data);
      if (data.length) {
        setSelectedCourse((old) => (old ?? data[0]));
      }
    } catch (error) {
      setStatus("Failed to load courses.");
    }
  }

  async function refreshCourseDetail(id: number) {
    try {
      const { data } = await api.get<CourseDetail>(`/api/courses/${id}`);
      setSelectedCourse(data);
    } catch {
      setStatus("Unable to load course details.");
    }
  }

  async function loadThreads() {
    try {
      const { data } = await api.get<Thread[]>("/api/communications/threads");
      setThreads(data);
    } catch {
      setStatus("Unable to load threads.");
    }
  }

  async function handleLogin(e: FormEvent) {
    e.preventDefault();
    try {
      const { data } = await api.post<AuthPayload>("/api/auth/login", {
        email: loginEmail,
        password: loginPassword,
      });
      setAuth(data);
      setStatus(`Logged in as ${data.name} (${data.role}).`);
      fetchProgressReport(data.id);
    } catch {
      setStatus("Authentication failed.");
    }
  }

  async function handleRegister(e: FormEvent) {
    e.preventDefault();
    try {
      const { data } = await api.post<AuthPayload>("/api/auth/register", {
        name: "Custom Learner",
        email: loginEmail,
        password: loginPassword,
        role: "STUDENT",
      });
      setAuth(data);
      setStatus("Registration successful, logged in.");
      fetchProgressReport(data.id);
    } catch (error: any) {
      setStatus(error?.response?.data?.message ?? "Registration failed.");
    }
  }

  async function enroll(courseId: number) {
    try {
      await api.post("/api/enrollments", {
        learnerName: auth?.name ?? "Guest Learner",
        learnerEmail: loginEmail,
        courseId,
      });
      setStatus("Enrollment submitted.");
    } catch {
      setStatus("Enrollment failed.");
    }
  }

  async function fetchProgressReport(learnerId: number) {
    try {
      const { data } = await api.get<ProgressReport>(`/api/progress/learner/${learnerId}`);
      setProgress(data);
    } catch {
      setProgress(null);
    }
  }

  async function postThread(e: FormEvent) {
    e.preventDefault();
    if (!threadTopic || !auth) return;
    try {
      await api.post(
        "/api/communications/threads",
        { topic: threadTopic },
        { headers }
      );
      setThreadTopic("");
      loadThreads();
      setStatus("Thread created.");
    } catch {
      setStatus("Unable to create thread.");
    }
  }

  async function sendMessage(e: FormEvent) {
    e.preventDefault();
    if (!selectedThreadId || !threadMessage || !auth) return;
    try {
      await api.post(
        `/api/communications/threads/${selectedThreadId}/messages`,
        { content: threadMessage },
        { headers }
      );
      setThreadMessage("");
      loadThreads();
      setStatus("Message posted.");
    } catch {
      setStatus("Unable to post message.");
    }
  }

  return (
    <div className="app-shell">
      <header>
        <div>
          <p className="eyebrow">Learning Experience Platform</p>
          <h1>Modular LMS</h1>
        </div>
        <div className="auth-card">
          <p className="hint">Login / register</p>
          <form className="auth-form" onSubmit={handleLogin}>
            <input value={loginEmail} onChange={(event) => setLoginEmail(event.target.value)} placeholder="email" />
            <input
              value={loginPassword}
              onChange={(event) => setLoginPassword(event.target.value)}
              type="password"
              placeholder="password"
            />
            <div className="auth-actions">
              <button type="submit">Login</button>
              <button type="button" onClick={handleRegister}>
                Register
              </button>
            </div>
          </form>
          {auth && <p className="auth-status">Signed in as {auth.name}</p>}
        </div>
      </header>

      <main>
        <section className="grid">
          <article className="panel">
            <div className="panel-header">
              <p className="eyebrow">Courses</p>
              <small>Select a course to review lessons &amp; materials.</small>
            </div>
            <div className="card-stack">
              {courses.map((course) => (
                <button
                  key={course.id}
                  className={`card ${selectedCourse?.id === course.id ? "primary" : ""}`}
                  onClick={() => setSelectedCourse(course as CourseDetail)}
                >
                  <div>
                    <strong>{course.title}</strong>
                    <p>{course.level} · {course.estimatedHours ?? "—"} hrs</p>
                  </div>
                  <span>{course.published ? "Published" : "Draft"}</span>
                </button>
              ))}
            </div>
          </article>

          <article className="panel">
            <div className="panel-header">
              <p className="eyebrow">Course Detail</p>
              <small>Lessons, materials, and material types exposed by the backend.</small>
            </div>
            {selectedCourse ? (
              <>
                <div className="detail-header">
                  <h2>{selectedCourse.title}</h2>
                  <button onClick={() => enroll(selectedCourse.id)}>Enroll</button>
                </div>
                <p>{selectedCourse.description}</p>
                <div className="tag-row">
                  <span className="tag">{selectedCourse.level}</span>
                  <span className="tag">{selectedCourse.lessons.length} lessons</span>
                  <span className="tag">{selectedCourse.materials.length} materials</span>
                </div>
                <div className="detail-grid">
                  <div>
                    <h3>Lessons</h3>
                    <ul>
                      {selectedCourse.lessons.map((lesson) => (
                        <li key={lesson.id}>
                          <strong>{lesson.sequenceNumber}. {lesson.title}</strong>
                          <p>{lesson.summary}</p>
                        </li>
                      ))}
                    </ul>
                  </div>
                  <div>
                    <h3>Materials</h3>
                    <ul>
                      {selectedCourse.materials.map((material) => (
                        <li key={material.id}>
                          <strong>{material.title}</strong>
                          <p>{material.type}</p>
                          {material.resourceUrl && (
                            <a target="_blank" rel="noreferrer" href={material.resourceUrl}>
                              Open resource
                            </a>
                          )}
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>
              </>
            ) : (
              <p>Select a course to see detail.</p>
            )}
          </article>
        </section>

        <section className="grid">
          <article className="panel">
            <div className="panel-header">
              <p className="eyebrow">Communication</p>
              <small>Create threads and exchange insight.</small>
            </div>
            <form className="form-inline" onSubmit={postThread}>
              <input value={threadTopic} onChange={(event) => setThreadTopic(event.target.value)} placeholder="Thread topic" />
              <button type="submit">New thread</button>
            </form>
            <div className="thread-list">
              {threads.map((thread) => (
                <div key={thread.id} className="thread-card">
                  <button onClick={() => setSelectedThreadId(thread.id)} className="link-button">
                    <h4>{thread.topic}</h4>
                  </button>
                  <p>{thread.creator?.name}</p>
                  <small>{thread.createdAt}</small>
                </div>
              ))}
            </div>
            {selectedThreadId && (
              <form className="form-inline" onSubmit={sendMessage}>
                <input value={threadMessage} onChange={(event) => setThreadMessage(event.target.value)} placeholder="Message" />
                <button type="submit">Send</button>
              </form>
            )}
          </article>

          <article className="panel">
            <div className="panel-header">
              <p className="eyebrow">Progress</p>
              <small>Track completions and quiz scores.</small>
            </div>
            {progress ? (
              <>
                <p>{progress.learnerName} · {progress.lessonsCompleted} completed lessons</p>
                <ul>
                  {Object.entries(progress.lessonSummary).map(([lesson, score]) => (
                    <li key={lesson}>
                      {lesson}: {score ?? "N/A"}%
                    </li>
                  ))}
                </ul>
              </>
            ) : (
              <p>Login to see your progress report.</p>
            )}
          </article>
        </section>

        {status && <p className="status">{status}</p>}
      </main>
    </div>
  );
}
