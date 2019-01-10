package gsdl;

public class SyntaxError extends RuntimeException
{
    private int line;
    private String message;
    public SyntaxError(int line, String message)
    {
        this.line = line;
        this.message = message;
    }
    @Override
    public String getMessage()
    {
        return "Error in line "+line+" "+message;
    }
}
