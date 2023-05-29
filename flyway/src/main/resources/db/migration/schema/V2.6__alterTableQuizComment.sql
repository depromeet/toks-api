--
-- table indexing
--

-- quiz_comment
CREATE INDEX idx_quiz_id ON quiz_comment (quiz_id);
CREATE INDEX idx_uid ON quiz_comment (uid);
