import java.util.ArrayList;
import java.util.List;

public final class Number {

    private final int numberBefore;

    private final int numberAfter;

    private final List<Integer> before;

    private final List<Integer> after;

    public Number(int numberBefore, int numberAfter,
                  List before, List after) {
        this.numberBefore = numberBefore;
        this.numberAfter = numberAfter;
        this.before = before;
        this.after = after;
    }

    public Number plus(Number other) {

        List<Integer> resultBefore = new ArrayList<>();
        List<Integer> resultAfter = new ArrayList<>();
        int maxBefore = Math.max(numberBefore, other.numberBefore);
        int maxAfter = Math.max(numberAfter, other.numberAfter);

        for (int i = 0; i < maxAfter - numberAfter; i++) {
            after.add(0);
        }

        for (int i = 0; i < maxAfter - other.numberAfter; i++) {
            other.after.add(0);
        }

        for (int i = 0; i < maxBefore - numberBefore; i++) {
            before.add(0, 0);
        }

        for (int i = 0; i < maxBefore - other.numberBefore; i++) {
            other.before.add(0, 0);
        }

        int excess = 0;
        for (int i = maxAfter - 1; i >= 0; i--) {
            resultAfter.add(0, (after.get(i) + other.after.get(i) + excess) % 10);

            if (after.get(i) + other.after.get(i) + excess > 9) {
                excess = 1;
            } else {
                excess = 0;
            }
        }

        for (int i = maxBefore - 1; i >= 0; i--) {
            resultBefore.add(0, (before.get(i) + other.before.get(i) + excess) % 10);

            if (before.get(i) + other.before.get(i) + excess > 9) {
                excess = 1;
            } else {
                excess = 0;
            }
        }

        if (excess > 0) {
            resultBefore.add(0, 1);
        }

        return new Number(resultBefore.size(), resultAfter.size(), resultBefore, resultAfter);
    }

    private List<Integer> union(List before, List after) {
        List result = new ArrayList();
        result.add(before);
        result.add(after);
        return result;
    }

    private List<Integer> maxNumber(Number num1,
                                    Number num2) {

        List<Integer> list;
        List<Integer> otherList;

        if (num1.equals(num2)) return union(num1.before, num2.after);
        if (num1.numberBefore > num2.numberBefore) return union(num1.before, num1.after);
        if (num1.numberBefore < num2.numberBefore) return union(num2.before, num2.after);
        else {
            list = union(num1.before, num1.after);
            otherList = union(num2.before, num2.after);
            int max = Math.max(list.size(), otherList.size());
            for (int i = 0; i < max - list.size(); i++) {
                list.add(0);
            }
            for (int i = 0; i < max - otherList.size(); i++) {
                otherList.add(0);
            }
            int i = 0;
            while (true) {
                if (list.get(i) > otherList.get(i)) return list;
                if (list.get(i) < otherList.get(i)) return otherList;
                i++;
            }
        }
    }

    @Override
    public String toString() {

        int i = 0;
        String strNumberBefore = "";
        while (numberBefore != i) {
            strNumberBefore = strNumberBefore + before.get(i);
            i++;
        }

        int j = 0;
        String strNumberAfter = "";
        while (numberAfter != j) {
            strNumberAfter = strNumberAfter + after.get(j);
            j++;
        }

        String number = strNumberBefore + "." + strNumberAfter;
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Number that = (Number) obj;

        if (numberBefore != that.numberBefore) return false;
        if (numberAfter != that.numberAfter) return false;
        if (!before.equals(that.before)) return false;
        return after.equals(that.after);
    }

    @Override
    public int hashCode() {
        int result = numberBefore;
        result = 31 * result + numberAfter;
        result = 31 * result + before.hashCode();
        result = 31 * result + after.hashCode();
        return result;
    }
}
