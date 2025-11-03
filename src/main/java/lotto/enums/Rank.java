package lotto.enums;

import java.util.Arrays;

public enum Rank {
    FIRST(6, 2_000_000_000L, false),
    SECOND(5, 30_000_000L, true),
    THIRD(5, 1_500_000L, false),
    FOURTH(4, 50_000L, false),
    FIFTH(3, 5_000L, false);

    private final int matchCount;
    private final long prize;
    private final boolean requireBonus;

    Rank(int matchCount, long prize, boolean requireBonus) {
        this.matchCount = matchCount;
        this.prize = prize;
        this.requireBonus = requireBonus;
    }

    public static Rank of(int matchCount, boolean bonusMatch) {
        return Arrays.stream(values())
            .filter(rank -> rank.matchCount == matchCount)
            .filter(rank -> rank.requireBonus == bonusMatch)
            .findFirst()
            .orElse(null);
    }

    public int getMatchCount() {
        return matchCount;
    }

    public long getPrize() {
        return prize;
    }

    public boolean isRequireBonus() {
        return requireBonus;
    }
}
