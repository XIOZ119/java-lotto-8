package lotto;

import lotto.enums.Rank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final List<Lotto> lottos;
    private final WinningLotto winningLotto;
    private final Map<Rank, Integer> results;

    public LottoResult(List<Lotto> lottos, WinningLotto winningLotto) {
        this.lottos = lottos;
        this.winningLotto = winningLotto;
        this.results = calculateResults();
    }

    private Map<Rank, Integer> calculateResults() {
        Map<Rank, Integer> resultMap = new HashMap<>();
        for (Lotto lotto : lottos) {
            Rank rank = winningLotto.match(lotto);
            if (rank != null) {
                resultMap.put(rank, resultMap.getOrDefault(rank, 0) + 1);
            }
        }
        return resultMap;
    }

    public int getCountByRank(Rank rank) {
        return results.getOrDefault(rank, 0);
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrize = calculateTotalPrize();
        return (totalPrize * 100.0) / purchaseAmount;
    }

    private long calculateTotalPrize() {
        long total = 0;
        for (Map.Entry<Rank, Integer> entry : results.entrySet()) {
            total += entry.getKey().getPrize() * entry.getValue();
        }
        return total;
    }
}
