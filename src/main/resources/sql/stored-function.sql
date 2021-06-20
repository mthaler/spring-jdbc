CREATE FUNCTION getFirstNameById(in_id INT) RETURNS VARCHAR(60) AS $$
	BEGIN
		RETURN (SELECT first_name FROM singer WHERE id = in_id);
	END
$$ LANGUAGE plpgsql;