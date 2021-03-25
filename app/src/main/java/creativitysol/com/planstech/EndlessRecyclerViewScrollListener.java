package creativitysol.com.planstech;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.Objects;

/**
 * Created by MuhameD on 8/1/2017.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;


    public abstract RecyclerView.LayoutManager getLayoutManager();

    public abstract void onLoadMore();

    private int getFirstVisibleItemPosition() {
        int lastVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            lastVisibleItemPosition = getFirstVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    private int getFirstVisibleItem(int[] lastVisibleItemPositions) {
        int minPosition = lastVisibleItemPositions[0];
        for (int i = 1; i < lastVisibleItemPositions.length; i++) {
            if (lastVisibleItemPositions[i] < minPosition) {
                minPosition = lastVisibleItemPositions[i];
            }
        }
        return minPosition;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = Objects.requireNonNull(recyclerView.getLayoutManager()).getItemCount();
            int firstVisibleItem = getFirstVisibleItemPosition();

            if (mLoading) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            int visibleThreshold = 5;
            if (!mLoading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                onLoadMore();

                mLoading = true;
            }
        }
    }

}