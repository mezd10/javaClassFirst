import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Number(String number) throws NumberFormatException {
        List<Integer> result = new ArrayList<>();
        int before = 0;
        int after = 0;
        int i = 0;
        Pattern p = Pattern.compile("((^-)|(^))((\\d+$)|(\\d+\\.\\d+$))");
        Matcher m = p.matcher(number);
        if (!m.find()) throw new NumberFormatException();
        if (m.group().charAt(0) == '-') {
            i = 2;
            result.add(-1 * (m.group().codePointAt(1) - '0'));
            before++;
        }
        for (; i < m.group().length(); i++) {
            if (m.group().charAt(i) == '.') continue;
            if (after != 0 || (i != 0 && m.group().charAt(i - 1) == '.')) {
                after++;
                result.add(m.group().codePointAt(i) - '0');
            } else {
                before++;
                result.add(m.group().codePointAt(i) - '0');
            }
        }
        this.before = before;
        this.after = after;
        this.numberUnion = result;
    }

    public Number plus(Number other) {

        List<Integer> result = new ArrayList<>();
        int maxAfter = Math.max(after, other.after);
        int sign = 0;

        if ((numberUnion.get(0) <= 0 && other.numberUnion.get(0) <= 0) || (numberUnion.get(0) >= 0 && other.numberUnion.get(0) >= 0)) {

            if (numberUnion.get(0) < 0) {
                sign = 1;
                int per = numberUnion.get(0);
                per *= -1;
                numberUnion.remove(0);
                numberUnion.add(0, per);
                per = other.numberUnion.get(0);
                per *= -1;
                other.numberUnion.remove(0);
                other.numberUnion.add(0, per);
            }

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
            result.add(0, sign);

            return new Number(result.size() - maxAfter, maxAfter, result);
        } else {

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
            if (maxNumber.get(0) < 0) {
                sign = 1;
            }

            int firstElement = maxNumber.get(0);
            if (firstElement < 0) {
                int per = maxNumber.get(0);
                per *= -1;
                maxNumber.remove(0);
                maxNumber.add(0, per);
            }


            if (minNumber.get(0) < 0) {
                int per = minNumber.get(0);
                per *= -1;
                minNumber.remove(0);
                minNumber.add(0, per);
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
            result.add(0, sign);
            return new Number(result.size() - maxAfter, maxAfter, result);
        }
    }


    private List<Integer> maxUnionNumber(Number numb1,
                                         Number numb2) {
        List<Integer> list;
        List<Integer> otherList;

        if (numb1.equals(numb2)) return numberUnion;
        if (numb1.before > numb2.before) return numb1.numberUnion;
        if (numb1.before < numb2.before) return numb2.numberUnion;
        if (numb1.before == numb2.before && Math.abs(numb1.numberUnion.get(0)) == Math.abs(numb2.numberUnion.get(0))) {
            if (numb1.numberUnion.get(0) >= 0) return numb1.numberUnion;
            else return numb2.numberUnion;
        } else {
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
            int sign = 0;
            int sign1 = 0;
            if (list.get(0) < 0) {
                sign = 1;
                int per = list.get(0);
                per *= -1;
                list.remove(0);
                list.add(0, per);
            }
            if (otherList.get(0) < 0) {
                sign1 = 1;
                int per = otherList.get(0);
                per *= -1;
                otherList.remove(0);
                otherList.add(0, per);
            }
            while (true) {
                if (list.get(i) > otherList.get(i)) {
                    if (sign == 1) {
                        int per = list.get(0);
                        per *= -1;
                        list.remove(0);
                        list.add(0, per);
                    }
                    return list;
                }
                if (list.get(i) < otherList.get(i)) {
                    if (sign1 == 1) {
                        int per = otherList.get(0);
                        per *= -1;
                        otherList.remove(0);
                        otherList.add(0, per);
                    }
                    return otherList;
                }
                i++;
            }
        }
    }

    public Number minus(Number other) {
        int sign = 0;
        if (numberUnion.get(0) >= 0 && other.numberUnion.get(0) >= 0) {

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
                sign = 1;
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
            result.add(0, sign);
            return new Number(result.size() - maxAfter, maxAfter, result);
        } else {
            int per = other.numberUnion.get(0);
            per *= -1;
            other.numberUnion.remove(0);
            other.numberUnion.add(0, per);
            Number number = this;
            Number number1 = other;
            return number.plus(number1);
        }
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (numberUnion.get(0) == 1) result.append("-");
        for (int i = 1; i < before; i++) {
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

