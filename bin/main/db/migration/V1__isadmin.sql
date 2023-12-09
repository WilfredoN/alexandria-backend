ALTER TABLE teachers
    ADD COLUMN IF NOT EXISTS is_admin BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE groups
    DROP COLUMN IF EXISTS teacher_id;
CREATE TABLE IF NOT EXISTS homework
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    deadline    TIMESTAMP    NOT NULL,
    group_id    INTEGER      NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    subject_id   INTEGER      NOT NULL REFERENCES subjects (id) ON DELETE CASCADE,
    teacher_id  INTEGER      NOT NULL REFERENCES teachers (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS homework_status
(
    id          SERIAL PRIMARY KEY,
    student_id  INTEGER   NOT NULL REFERENCES students (id) ON DELETE CASCADE,
    homework_id INTEGER   NOT NULL REFERENCES homework (id) ON DELETE CASCADE,
    is_done     BOOLEAN   NOT NULL DEFAULT FALSE,
    marked_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (student_id, homework_id)
);
