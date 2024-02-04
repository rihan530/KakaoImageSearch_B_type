package com.example.kakaoimagesearch_b_type

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kakaoimagesearch_b_type.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // ActivityMainBinding은 MainActivity의 레이아웃 바인딩 객체
    // 바인딩을 통해 findViewById를 사용하지 않고 해당 레이아웃의 뷰에 직접 접근 가능
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ActivityMainBinding 객체를 초기화
        // 이 객체는 layoutInflater를 사용하여 activity_main.xml 레이아웃을 인플레이트
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 초기화된 바인딩 객체의 root 뷰를 활동의 콘텐츠 뷰로 설정
        setContentView(binding.root)

        // R.id.nav_host_fragment_activity_main ID를 가진 NavHostFragment의 NavController를 호출
        // NavController는 Navigation Component에서 프래그먼트 간의 탐색을 관리
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // BottomNavigationView를 NavController와 연결
        // 탐색 메뉴 항목이 클릭될 때 알맞은 프래그먼트로 이동
        NavigationUI.setupWithNavController(binding.navView, navController)
    }
}