package winnie.commands;

import java.io.PrintStream;
import java.io.Serializable;

public abstract class AbstractCommand implements Command {
    private PrintStream out;

    AbstractCommand(){
        this.out = System.out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public PrintStream getOut() {
        return out;
    }
}
