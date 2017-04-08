package by.it.radivonik.calculator;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Created by Radivonik on 13.03.2017.
 */
public class Var implements IVariable {
   private static String fileVars = System.getProperty("user.dir") + "/src/by/it/radivonik/calculator/vars.txt";
   private static boolean isLoadFile = false;
   private static Map<String,Var> vars = new HashMap<String,Var>(){
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder("");
            for (Map.Entry<String,Var> v: this.entrySet()) {
                res.append(String.format("%s=%s\n",v.getKey(),v.getValue()));
            }
            return res.toString();
        }
    };

    @Override
    public void fromString(String str) throws ParseException {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    static Var createVar(String str) throws ParseException {
        if (str == null)
            throw new ParseException("Пустая строка");
        Var res;
        if (str.matches(IPatterns.ExNumber))
            res = new VarFloat(str);
        else if (str.matches(IPatterns.ExVector))
            res = new VarVector(str);
        else if (str.matches(IPatterns.exMatrix))
            res = new VarMatrix(str);
        else
            res = vars.get(str);
        return res;
    }

    static Var setVar(String name, String v) throws ParseException {
        Var res = null;
        if (v == null)
            vars.remove(name);
        else {
            res = createVar(v);
            vars.put(name, res);
        }
        return res;
    }

    static String getPrintVar() {
        return vars.toString();
    }

    static String getSortVar() {
        SortedMap<String,Var> varsSorted = new TreeMap<String,Var>(vars) {
            @Override
            public String toString() {
                StringBuilder res = new StringBuilder("");
                for (Map.Entry<String,Var> v: this.entrySet()) {
                    res.append(String.format("%s=%s\n",v.getKey(),v.getValue()));
                }
                return res.toString();
            }

        };
        return varsSorted.toString();
    }

    public void loadFromFile() throws ParseException {
        if (isLoadFile)
            return;

        vars.clear();
        isLoadFile = true;

        if (!Files.exists(Paths.get(fileVars)))
            return;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileVars))) {
            String line;
            while ((line = fileReader.readLine()) != null)
                Calc.calculate(line);
        }
        catch (IOException e) {
            throw new ParseException("Не прочитан файл с переменными (" + e.getMessage() + ")");
        }
    }

    public void saveToFile() throws ParseException {
        if (vars.size() == 0)
            return;

        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(fileVars))) {
            fileWriter.print(vars);
        }
        catch (IOException e) {
            throw new ParseException("Не сохранен файл с переменными (" + e.getMessage() + ")");
        }
    }
}
