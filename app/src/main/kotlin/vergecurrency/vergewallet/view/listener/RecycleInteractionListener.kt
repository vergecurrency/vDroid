import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.view.adapter.TransactionRecycleAdapter


class RecycleTouchListener(context: Context?, rv: RecyclerView, private val clickListener: ClickListener?) : OnItemTouchListener {
    private val gestureDetector: GestureDetector
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child: View? = rv.findChildViewUnder(e.x, e.y)
        val adapter: TransactionRecycleAdapter = rv.adapter as TransactionRecycleAdapter;
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            val obj = adapter.getObject(rv.getChildAdapterPosition(child))
            if (obj is Transaction) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child), obj)
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    interface ClickListener {
        fun onClick(view: View?, position: Int, tx: Transaction)
        fun onLongClick(view: View?, position: Int, tx: Transaction)
    }

    init {
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child: View? = rv.findChildViewUnder(e.x, e.y)
                val adapter: TransactionRecycleAdapter = rv.adapter as TransactionRecycleAdapter;
                val obj = adapter.getObject(rv.getChildAdapterPosition(child!!))
                if (child != null && clickListener != null && obj is Transaction) {
                    clickListener.onLongClick(child, rv.getChildAdapterPosition(child), obj)
                }
            }
        })
    }
}