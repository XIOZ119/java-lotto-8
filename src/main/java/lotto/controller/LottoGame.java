package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.LottoResult;
import lotto.domain.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class LottoGame {
    private final LottoMachine lottoMachine;

    public LottoGame() {
        this.lottoMachine = new LottoMachine();
    }

    public void play() {
        int purchaseAmount = readPurchaseAmountWithRetry();
        List<Lotto> lottos = lottoMachine.purchaseLottos(purchaseAmount);
        OutputView.printLottos(lottos);

        WinningLotto winningLotto = createWinningLottoWithRetry();
        LottoResult result = new LottoResult(lottos, winningLotto);
        OutputView.printResults(result, purchaseAmount);
    }

    private int readPurchaseAmountWithRetry() {
        return retryOnException(InputView::readPurchaseAmount);
    }

    private WinningLotto createWinningLottoWithRetry() {
        List<Integer> winningNumbers = readWinningNumbersWithRetry();
        int bonusNumber = readBonusNumberWithRetry(winningNumbers);
        return new WinningLotto(new Lotto(winningNumbers), bonusNumber);
    }

    private List<Integer> readWinningNumbersWithRetry() {
        return retryOnException(() -> {
            List<Integer> numbers = InputView.readWinningNumbers();
            new Lotto(numbers);
            return numbers;
        });
    }

    private int readBonusNumberWithRetry(List<Integer> winningNumbers) {
        return retryOnException(() -> {
            int bonus = InputView.readBonusNumber();
            validateBonusNumber(bonus, winningNumbers);
            return bonus;
        });
    }

    private void validateBonusNumber(int bonusNumber, List<Integer> winningNumbers) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private <T> T retryOnException(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                String message = e.getMessage();
                if (message == null) {
                    message = "[ERROR] 잘못된 입력입니다.";
                }
                OutputView.printError(message);
            }
        }
    }
}
