package lotto.domain;

import lotto.domain.enums.Rank;

public class WinningLotto {
    private static final int LOTTO_NUMBER_MIN = 1;
    private static final int LOTTO_NUMBER_MAX = 45;

    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
        validateBonusNumber();
    }

    private void validateBonusNumber() {
        validateBonusRange();
        validateBonusDuplicate();
    }

    private void validateBonusRange() {
        if (bonusNumber < LOTTO_NUMBER_MIN || bonusNumber > LOTTO_NUMBER_MAX) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusDuplicate() {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public Rank match(Lotto lotto) {
        int matchCount = lotto.countMatchingNumbers(winningNumbers.getNumbers());
        boolean bonusMatch = lotto.contains(bonusNumber);
        return Rank.of(matchCount, bonusMatch);
    }
}
