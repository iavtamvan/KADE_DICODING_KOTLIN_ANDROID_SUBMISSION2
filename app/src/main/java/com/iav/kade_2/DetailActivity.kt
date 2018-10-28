package com.iav.kade_2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.bumptech.glide.Glide
import com.iav.kade_2.Model.Item
import com.iav.kade_2.Rest.ApiService
import com.iav.kade_2.Rest.RetroConfig
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {
    private var items: MutableList<Item> = mutableListOf()
    private var list: ArrayList<Item> = arrayListOf()
    private var menuItem: Menu? = null
    private var nilai: String = "not"
    var posisi = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        getDataParcel()
    }

    private fun getDataParcel() {

        list = intent.getParcelableArrayListExtra("list")
        posisi = intent.getStringExtra("posisi").toInt()
        tv_detail_team_home.text = list.get(posisi).teamHome
        tv_detail_team_away.text = list.get(posisi).teamAway
        tv_detail_score_home.text = list.get(posisi).scoreHome
        tv_detail_score_away.text = list.get(posisi).scoreAway
        tv_formasi_home.text = list.get(posisi).homeFormation
        tv_formasi_away.text = list.get(posisi).awayFormation
        tv_goal_home.text = list.get(posisi).goalHome
        tv_goal_away.text = list.get(posisi).goalAway
        tv_shots_home.text = list.get(posisi).shotsHome
        tv_shots_away.text = list.get(posisi).shotsAway
        tv_keeper_home.text = list.get(posisi).keperHome
        tv_keeper_away.text = list.get(posisi).keperAway
        tv_defend_home.text = list.get(posisi).defenderHome
        tv_defend_away.text = list.get(posisi).defenderAway
        tv_midle_home.text = list.get(posisi).midleHome
        tv_midle_away.text = list.get(posisi).midleAway
        tv_forward_home.text = list.get(posisi).forwardHome
        tv_forward_away.text = list.get(posisi).forwardAway
        tv_detail_tanggal.text = list.get(posisi).dateEvent
        getHomeTeam()
        getawayTeam()
    }

    private fun getHomeTeam() {
        val service: ApiService = RetroConfig.provideApi()
        service.getTeam("" + list.get(posisi).idHomeTeam)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            items.clear()
                            items = result.teams as MutableList<Item>
                            val images = (items.get(0).teamBadge)

                            Glide.with(applicationContext)
                                    .load(images)
                                    .into(img_home)

                        },
                        { error ->
                            toast("" + error.message)
                        }
                )
    }

    private fun getawayTeam() {
        val service: ApiService = RetroConfig.provideApi()
        service.getTeam("" + list.get(posisi).idAwayTeam)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            items.clear()
                            items = result.teams as MutableList<Item>
                            val images = (items.get(0).teamBadge)

                            Glide.with(applicationContext)
                                    .load(images)
                                    .into(img_away)

                        },
                        { error ->
                            toast("" + error.message)
                        }
                )
    }
}
