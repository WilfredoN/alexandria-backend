CREATE TABLE subjects_teachers
(
    teacher_id INTEGER REFERENCES teachers (id) ON DELETE CASCADE,
    subject_id   INTEGER REFERENCES subjects (id) ON DELETE CASCADE,
    PRIMARY KEY (teacher_id, subject_id)
);