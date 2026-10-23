package LibraryPal.demo.dto;

public class CoordinatorStatisticsResponse {

    private long totalRequests;
    private long pendingRequests;
    private long approvedRequests;
    private long completedRequests;
    private long cancelledRequests;

    public CoordinatorStatisticsResponse(
            long totalRequests,
            long pendingRequests,
            long approvedRequests,
            long completedRequests,
            long cancelledRequests) {
        this.totalRequests = totalRequests;
        this.pendingRequests = pendingRequests;
        this.approvedRequests = approvedRequests;
        this.completedRequests = completedRequests;
        this.cancelledRequests = cancelledRequests;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getPendingRequests() {
        return pendingRequests;
    }

    public long getApprovedRequests() {
        return approvedRequests;
    }

    public long getCompletedRequests() {
        return completedRequests;
    }

    public long getCancelledRequests() {
        return cancelledRequests;
    }
}