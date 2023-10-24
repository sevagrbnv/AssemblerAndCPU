package execute_components;

public enum State {
    WAIT,
    READ_COMMAND,
    DECODE_COMMAND,
    SET_ARGS,
    EXECUTE_COMMAND,
    WRITE_RESUlT
}
