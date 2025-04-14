
interface Command {
    void execute();
    void undo();
}

class Light {
    public void turnOn() {
        System.out.println("[Light] Turning ON");
    }

    public void turnOff() {
        System.out.println("[Light] Turning OFF");
    }
}

class Thermostat {
    private int temperature = 20;
    private int prevTemp;

    public void setTemperature(int temp) {
        prevTemp = temperature;
        temperature = temp;
        System.out.println("[Thermostat] Setting temperature to " + temp + "°C");
    }

    public void revertTemperature() {
        temperature = prevTemp;
        System.out.println("[Thermostat] Reverting to previous temperature (" + temperature + "°C)");
    }
}

class TurnOnLightCommand implements Command {
    private Light light;

    public TurnOnLightCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }

    public void undo() {
        light.turnOff();
    }
}

class SetThermostatCommand implements Command {
    private Thermostat thermostat;
    private int temperature;

    public SetThermostatCommand(Thermostat thermostat, int temperature) {
        this.thermostat = thermostat;
        this.temperature = temperature;
    }

    public void execute() {
        thermostat.setTemperature(temperature);
    }

    public void undo() {
        thermostat.revertTemperature();
    }
}

import java.util.HashMap;
import java.util.Map;

class SmartHomeRemoteControl {
    private Map<String, Command> slots = new HashMap<>();
    private Command lastCommand;

    public void setCommand(String slot, Command command) {
        slots.put(slot, command);
    }

    public void pressButton(String slot) {
        Command command = slots.get(slot);
        if (command != null) {
            command.execute();
            lastCommand = command;
        } else {
            System.out.println("No command assigned to slot: " + slot);
        }
    }

    public void undoButton() {
        if (lastCommand != null) {
            System.out.println("Undo last command");
            lastCommand.undo();
        }
    }
}

public class CommandPatternDemo {
    public static void main(String[] args) {
        Light light = new Light();
        Thermostat thermostat = new Thermostat();

        SmartHomeRemoteControl remote = new SmartHomeRemoteControl();

        remote.setCommand("light", new TurnOnLightCommand(light));
        remote.setCommand("temp", new SetThermostatCommand(thermostat, 22));

        remote.pressButton("light");
        remote.pressButton("temp");
        remote.undoButton();
    }
}
