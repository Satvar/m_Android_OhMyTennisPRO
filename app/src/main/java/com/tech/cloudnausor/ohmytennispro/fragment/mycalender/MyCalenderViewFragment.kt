package com.tech.cloudnausor.ohmytennispro.fragment.mycalender

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer

import com.tech.cloudnausor.ohmytennispro.R
import com.tech.cloudnausor.ohmytennispro.activity.CalenderAvaibility
import com.tech.cloudnausor.ohmytennispro.activity.coachuserreservation.CoachUserReservation
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccountHomeFragment
import com.tech.cloudnausor.ohmytennispro.model.CalenderModel
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel
import com.tech.cloudnausor.ohmytennispro.response.CalenderResponseData
import com.tech.cloudnausor.ohmytennispro.response.GetCoachCollectiveOnDemandResponseData
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.example_3_calendar_day.view.*
import kotlinx.android.synthetic.main.example_3_event_item_view.view.*
import kotlinx.android.synthetic.main.exmaple_3_fragment.*
import kotlinx.android.synthetic.main.exmaple_3_fragment.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

data class Event(val id: String, val text: String, val date: LocalDate)

 fun newInstance(text: String): MyCalenderViewFragment {
    var mFragment = MyCalenderViewFragment()
    var mBundle = Bundle()
    mBundle.putString(Constant.TEXT_FRAGMENT, text)
    mFragment.arguments = mBundle
    return mFragment
}

internal var apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

class Example3EventsAdapter(val onClick: (Event) -> Unit) :
        RecyclerView.Adapter<Example3EventsAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<Event>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
        val view_main = LayoutInflater.from(parent.context).inflate(R.layout.example_3_event_item_view, parent, false)

        return Example3EventsViewHolder(view_main)
    }

    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class Example3EventsViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {

                onClick(events[adapterPosition])
            }
        }

        fun bind(event: Event) {
            itemView.itemEventText.text = event.text
        }
    }

}

class MyCalenderViewFragment : Fragment() {

    private val eventsAdapter = Example3EventsAdapter {
//        AlertDialog.Builder(requireContext())
//                .setMessage(R.string.example_3_dialog_delete_confirmation)
//                .setPositiveButton(R.string.delete) { _, _ ->
//                    deleteEvent(it)
//                }
//                .setNegativeButton(R.string.close, null)
//                .show()
    }

    private val inputDialog by lazy {
        val editText = AppCompatEditText(requireContext())
        val layout = FrameLayout(requireContext()).apply {
            // Setting the padding on the EditText only pads the input area
            // not the entire EditText so we wrap it in a FrameLayout.
            val padding = dpToPx(20, requireContext())
            setPadding(padding, padding, padding, padding)
            addView(editText, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.example_3_input_dialog_title))
                .setView(layout)
                .setPositiveButton(R.string.save) { _, _ ->
                    saveEvent(editText.text.toString())
                    // Prepare EditText for reuse.
                    editText.setText("")
                }
                .setNegativeButton(R.string.close, null)
                .create()
                .apply {
                    setOnShowListener {
                        // Show the keyboard
                        editText.requestFocus()
                        context.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    }
                    setOnDismissListener {
                        // Hide the keyboard
                        context.inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    }
                }
    }

//    override val titleRes: Int = R.string.example_3_title

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()
    lateinit var session: SessionManagement
    lateinit var sharedPreferences: SharedPreferences
    lateinit   var editor: SharedPreferences.Editor
    internal var edit_sting: String? = null
    internal var coachid_: String? = null
    internal var uPassword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view_myaccformthree = inflater.inflate(R.layout.exmaple_3_fragment, container, false)

        sharedPreferences = context!!.getSharedPreferences("Reg", 0)
        editor = sharedPreferences.edit()
        session = SessionManagement(context)




        if (sharedPreferences.contains("KEY_Coach_ID")) {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "")

        }
        if (sharedPreferences.contains("Email")) {
            uPassword = sharedPreferences.getString("Email", "")

        }

        if (sharedPreferences.contains("IsMyEditString")) {
            edit_sting = sharedPreferences.getString("IsMyEditString", "")
        }
//        apiInterface.getcalender("13").enqueue(object : Callback<CalenderResponseData> {
//            override fun onResponse(call: Call<CalenderResponseData>, response: Response<CalenderResponseData>) {
//                assert(response.body() != null)
//                System.out.println("response.body()" + Gson().toJson(response.body()) )
//                if (response.body()?.getIsSuccess().toString() == "true") {
//                    var calenderModels:ArrayList<CalenderModel> = ArrayList<CalenderModel>();3
//                    if (response.body()!!.getGetIndiCoachDetailsModel().getCalenderModels() != null) {
//                    calenderModels.addAll(response.body()!!.getGetIndiCoachDetailsModel().getCalenderModels())
//                        for (item in calenderModels) {
//
//                            if (!item.getStart().equals(null)) {
//                                val utcDateStr = item.getStart()
//                                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                                var date1: Date? = null
//                                try {
//                                    date1 = sdf.parse(utcDateStr)
//                                    println("MM/dd/yyyy formatted date : " + SimpleDateFormat("yyyy").format(date1!!))
//                                    println("yyyy-MM-dd formatted date : " + SimpleDateFormat("yyyy-MM-dd").format(date1))
//
//                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                    selectedDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd").format(date1), formatter)
//
//                                    selectedDate?.let {
//                                        if (!item.getTitle().equals(null)) {
//                                            events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), item.getTitle(), it))
//                                            updateAdapterForDate(it)
////            updateAdapterForDate(it)}
//                                        }else{
//                                            System.out.println("events"  )
//                                        }
//                                    }
//                                } catch (e: ParseException) {
//                                    e.printStackTrace()
//                                    println("e.printStackTrace() " + e.printStackTrace())
//
//                                }
//
//                            }else{
//                                System.out.println("utcDateStr"  )
//                            }
//                        }
//                    }else {
//                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_LONG).show()
//                    }
//                    //                 System.out.println("- --> " + new Gson().toJson(response.body()));
//                    //                 IndiCourseData indiCourseData = response.body().getGetIndiCoachDetailsModel();
//                    //                 GetIndiCoachDetailsModel getIndiCoachDetailsModel = indiCourseData.getIndicouresedata().get(0);
//                    //                 Indi_Course_Description.setText(getIndiCoachDetailsModel.getDescription());
//                    //                 Indi_Course_Transport.setText(getIndiCoachDetailsModel.getMode_of_Transport());
//                    //                 Indi_Course_Tech.setText(getIndiCoachDetailsModel.getTechnical_provided());
//                    //                 Indi_Course_Hr.setText(getIndiCoachDetailsModel.getPrice_min());
//                    //                 Indi_Course_Ten_Hr.setText(getIndiCoachDetailsModel.getPrice_max());
//                    //                 Indi_Course_Video_1.setText(getIndiCoachDetailsModel.getVideo());
//                } else {
//                    Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<CalenderResponseData>, t: Throwable) {
//                println("---> $call $t")
//            }
//        })

//        val utcDateStr = "2019-11-10T12:00:00Z"
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//        var date1: Date? = null
//        try {
//            date1 = sdf.parse(utcDateStr)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        println("MM/dd/yyyy formatted date : " + SimpleDateFormat("yyyy").format(date1!!))
//        println("yyyy-MM-dd formatted date : " + SimpleDateFormat("yyyy-MM-dd").format(date1))
//
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        selectedDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd").format(date1), formatter)
//        selectedDate?.let {
//            events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), "bala", it))
////            updateAdapterForDate(it)
//        }
        return view_myaccformthree
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exThreeRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        exThreeRv.adapter = eventsAdapter
        exThreeRv.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        val daysOfWeek = daysOfWeekFromLocale()
        val weekData :ArrayList<String> = ArrayList()
        weekData.add("Lun");
        weekData.add("Mar");
        weekData.add("Mer");
        weekData.add("Jev");
        weekData.add("Ven");
        weekData.add("Sam");
        weekData.add("Dim");

        val currentMonth = YearMonth.now()

//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.first())
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(0))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(1))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(2))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(3))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(4))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(5))
//        System.out.println("daysOfWeek.first()---> "+daysOfWeek.get(6))


        exThreeCalendar.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(100), daysOfWeek.get(1))
        exThreeCalendar.scrollToMonth(currentMonth)


        if (savedInstanceState == null) {
            exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val textView = view.exThreeDayText
            val dotView = view.exThreeDotView

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {

                        selectDate(day.date)
                    }
                }
            }
        }

        exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val dotView = container.dotView

                textView.text = day.date.dayOfMonth.toString()

                System.out.println("")

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.whitecolor)
                            textView.setBackgroundResource(R.drawable.example_3_selected_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        exThreeCalendar.monthScrollListener = {

            var month:String =   if (it.year == today.year) {
                titleFormatter.format(it.yearMonth)
//                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }

            if(month.contains("January")){
               month = month.replace("January","Janvier")
             }else if(month.contains("Jan")){
                month= month.replace("Jan","Janvier")
            }
            if(month.contains("February")){
                month=  month.replace("February","Février")
            }else if(month.contains("Feb")){
                month= month.replace("Feb","Février")
            }
            if(month.contains("March")){
                month =  month.replace("March","Mars")
            }else if(month.contains("Mar")){
                month =  month.replace("Mar","Mars")
            }
            if(month.contains("April")){
                month =  month.replace("April","Avril")
            }else if(month.contains("Apr")){
                month =  month.replace("Apr","Avril")
            }

            if(month.contains("May")){
                month = month.replace("May","Mai")
            }else if(month.contains("May")){
                month =  month.replace("May","Mai")
            }
            if(month.contains("June")){
                month = month.replace("June","Juin")
            }else if(month.contains("Jun")){
                month =  month.replace("Jun","Juin")
            }
            if(month.contains("July")){
                month =   month.replace("July","Juillet")
            }else if(month.contains("Jul")){
                month =  month.replace("Jul","Juillet")
            }
            if(month.contains("August")){
                month =  month.replace("August","Août")
            }else if(month.contains("Aug")){
                month =   month.replace("Aug","Août")
            }
            if(month.contains("September")){
                month =  month.replace("September","Septembre")
            }else if(month.contains("Sep")){
                month = month.replace("Sep","Septembre")
            }
            if(month.contains("October")){
                month =  month.replace("October","Octobre")
            }else if(month.contains("Oct")){
                month =  month.replace("Oct","Octobre")
            }
            if(month.contains("November")){
                month = month.replace("November","Novembre")
            }else if(month.contains("Nov")){
                month =  month.replace("Nov","Novembre")
            }
            if(month.contains("December")){
                month =  month.replace("December","Décembre")
            }else if(month.contains("Dec")){
                month =  month.replace("Dec","Décembre")
            }



            requireActivity().homeToolbartitle.text =  month

            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legendLayout
        }
        exThreeCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
//                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.text = weekData[index].toString()

                        tv.setTextColorRes(R.color.example_3_black)
                    }
                }
            }
        }

        exThreeAddButton.setOnClickListener {
            val intent = Intent(activity, CalenderAvaibility::class.java)
            startActivity(intent)
//            inputDialog.show()
        }
setTimetable()
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            System.out.println("selectedDate = date" + selectedDate)
            oldDate?.let { exThreeCalendar.notifyDateChanged(it) }
            exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun setTimetable(){

        (context as RealMainPageActivity).showprocess()

        apiInterface.getcalender(coachid_).enqueue(object : Callback<CalenderResponseData> {
            override fun onResponse(call: Call<CalenderResponseData>, response: Response<CalenderResponseData>) {
                assert(response.body() != null)
                System.out.println("response.body()" + Gson().toJson(response.body()) )
                if (response.body()?.getIsSuccess().toString() == "true") {
                    var calenderModels:ArrayList<CalenderModel> = ArrayList<CalenderModel>();
                    if (response.body()!!.getGetIndiCoachDetailsModel().getCalenderModels() != null) {
                        calenderModels.addAll(response.body()!!.getGetIndiCoachDetailsModel().getCalenderModels())


                        Log.e(TAG,Gson().toJson(calenderModels) );

                        for (item in calenderModels) {

                            if (item.getStart() != null && !item.getStart().equals("")  ) {
                                System.out.println("item.id--> "+item.id)
                                val utcDateStr = item.getStart()
                                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                var date1: Date?
                                try {
                                    date1 = sdf.parse(utcDateStr)
                                    println("MM/dd/yyyy formatted date : " + SimpleDateFormat("yyyy").format(date1!!))
                                    println("yyyy-MM-dd formatted date : " + SimpleDateFormat("yyyy-MM-dd").format(date1))

                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    selectedDate = LocalDate.parse(SimpleDateFormat("yyyy-MM-dd").format(date1), formatter)

                                    selectedDate?.let {
                                        if (!item.getTitle().equals(null)) {
                                            events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), item.getTitle(), it))
                                            updateAdapterForDate(it)
                                            selectDate(today)
//            updateAdapterForDate(it)}
                                        }else{
                                            System.out.println("events"  )
                                        }
                                    }
                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                    (context as RealMainPageActivity).dismissprocess()
                                    println("e.printStackTrace() " + e.printStackTrace())

                                }

                            }else{
                                System.out.println("utcDateStr"  )
                            }
                        }
                        (context as RealMainPageActivity).dismissprocess()
                    }else {
                        (context as RealMainPageActivity).dismissprocess()
                        Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_LONG).show()
                    }
                    //                 System.out.println("- --> " + new Gson().toJson(response.body()));
                    //                 IndiCourseData indiCourseData = response.body().getGetIndiCoachDetailsModel();
                    //                 GetIndiCoachDetailsModel getIndiCoachDetailsModel = indiCourseData.getIndicouresedata().get(0);
                    //                 Indi_Course_Description.setText(getIndiCoachDetailsModel.getDescription());
                    //                 Indi_Course_Transport.setText(getIndiCoachDetailsModel.getMode_of_Transport());
                    //                 Indi_Course_Tech.setText(getIndiCoachDetailsModel.getTechnical_provided());
                    //                 Indi_Course_Hr.setText(getIndiCoachDetailsModel.getPrice_min());
                    //                 Indi_Course_Ten_Hr.setText(getIndiCoachDetailsModel.getPrice_max());
                    //                 Indi_Course_Video_1.setText(getIndiCoachDetailsModel.getVideo());
                } else {
                    (context as RealMainPageActivity).dismissprocess()
                    Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CalenderResponseData>, t: Throwable) {
                println("---> $call $t")
            }
        })

    }

    private fun saveEvent(text: String) {
        if (text.isBlank()) {
            Toast.makeText(requireContext(), R.string.example_3_empty_input_text, Toast.LENGTH_LONG).show()
        } else {
            selectedDate?.let {
                events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), text, it))
                updateAdapterForDate(it)
            }
        }
    }

    private fun deleteEvent(event: Event) {
        val date = event.date
        events[date] = events[date].orEmpty().minus(event)
        updateAdapterForDate(date)
    }

    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.events.clear()
        eventsAdapter.events.addAll(events[date].orEmpty())
        eventsAdapter.notifyDataSetChanged()
        exThreeSelectedDateText.text = selectionFormatter.format(date)
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).homeToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.whitecolor))
        (activity as AppCompatActivity).homeToolbartitle.setTextColor(requireContext().getColorCompat(R.color.colorPrimary))

        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).homeToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.colorPrimary))
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }

    override fun onResume() {
        super.onResume()
        setTimetable()
    }

    override fun onPause() {
        super.onPause()
    }
}

