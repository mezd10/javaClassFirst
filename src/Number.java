import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Number {

    private final int before;

    private final int after;

    private final List<Integer> numberUnion;

    public Number(int before, int after,
                  List<Integer> numberUnion) {

        this.before = before;
        this.after = after;
        this.numberUnion = numberUnion;
    }

    public Number plus(Number other) {

        List<Integer> result = new ArrayList<>();
        int maxAfter = Math.max(after, other.after);

        for (int i = 0; i < maxAfter - after; i++) {
            numberUnion.add(0);
        }

        for (int i = 0; i < maxAfter - other.after; i++) {
            other.numberUnion.add(0);
        }

        int maxBefore = Math.max(before, other.before);

        for (int i = 0; i < maxBefore - before; i++) {
            numberUnion.add(0, 0);
        }

        for (int i = 0; i < maxBefore - other.before; i++) {
            other.numberUnion.add(0, 0);
        }

        int excess = 0;

        for (int i = maxBefore + maxAfter - 1; i >= 0; i--) {
            result.add(0, (numberUnion.get(i) + other.numberUnion.get(i) + excess) % 10);

            if (numberUnion.get(i) + other.numberUnion.get(i) + excess > 9) {
                excess = 1;
            } else {
                excess = 0;
            }
        }

        if (excess > 0) {
            result.add(0, 1);
        }
        return new Number(result.size() - maxAfter, maxAfter, result);
    }

    private List<Integer> maxUnionNumber(Number numb1,
                                         Number numb2) {
        List<Integer> list;
        List<Integer> otherList;

        if (numb1.equals(numb2)) return numberUnion;
        if (numb1.before > numb2.before) return numb1.numberUnion;
        if (numb1.before < numb2.before) return numb2.numberUnion;
        else {
            list = numb1.numberUnion;
            otherList = numb2.numberUnion;
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

    public Number minus(Number other) {

        List<Integer> result = new ArrayList<>();
        int maxAfter = Math.max(after, other.after);

        for (int i = 0; i < maxAfter - after; i++) {
            numberUnion.add(0);
        }

        for (int i = 0; i < maxAfter - other.after; i++) {
            other.numberUnion.add(0);
        }

        int maxBefore = Math.max(before, other.before);

        for (int i = 0; i < maxBefore - before; i++) {
            numberUnion.add(0, 0);
        }

        for (int i = 0; i < maxBefore - other.before; i++) {
            other.numberUnion.add(0, 0);
        }

        List<Integer> maxNumber = maxUnionNumber(this, other);
        List<Integer> minNumber;

        if (maxNumber.equals(this.numberUnion)) {
            minNumber = other.numberUnion;
        } else {
            minNumber = numberUnion;
        }

        Collections.reverse(maxNumber);
        Collections.reverse(minNumber);

        int shortcoming = 0;

        for (int i = 0; i < maxNumber.size(); i++) {
            if (maxNumber.get(i) - minNumber.get(i) - shortcoming < 0) {
                result.add(0, maxNumber.get(i) - minNumber.get(i) - shortcoming + 10);
                shortcoming = 1;
            } else {
                result.add(0, maxNumber.get(i) - minNumber.get(i) - shortcoming);
                shortcoming = 0;
            }
        }
        if (result.get(0) == 0) {
            result.remove(0);
        }
        return new Number(result.size() - maxAfter, maxAfter, result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < before; i++) {
            result.append(numberUnion.get(i));
        }
        if (after != 0) result.append(".");
        for (int j = before; j < before + after; j++) {
            result.append(numberUnion.get(j));
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number = (Number) o;

        if (before != number.before) return false;
        if (after != number.after) return false;
        return numberUnion.equals(number.numberUnion);

    }

    @Override
    public int hashCode() {
        int result = before;
        result = 31 * result + after;
        result = 31 * result + numberUnion.hashCode();
        return result;
    }
}

