package lotto.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputView {
    public static int readPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        return readIntInput();
    }

    public static List<Integer> readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return readNumberListInput();
    }

    public static int readBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return readIntInput();
    }

    private static int readIntInput() {
        String input = Console.readLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야 합니다.");
        }
    }

    private static List<Integer> readNumberListInput() {
        String input = Console.readLine();
        return parseNumbers(input);
    }

    private static List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split(","))
            .map(String::trim)
            .map(InputView::parseNumber)
            .collect(Collectors.toList());
    }

    private static int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야 합니다.");
        }
    }
}
