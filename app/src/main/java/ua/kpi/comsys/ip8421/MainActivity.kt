package ua.kpi.comsys.ip8421

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Error

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tabLayout : TabLayout = findViewById(R.id.id_tabLayout);

        var viewPager2 : ViewPager2 = findViewById(R.id.id_viewPager2);

        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2;
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> Page1();
                    1 -> Page2();
                    else -> error("Error");
                }
            }
        }

        tabLayout.getTabAt(0)?.apply {
            customView?.setOnClickListener(){
                viewPager2.setCurrentItem(0)
            }
        }

        tabLayout.getTabAt(1)?.apply {
            customView?.setOnClickListener(){
                viewPager2.setCurrentItem(1)
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            viewPager2.setCurrentItem(tab.position, true)
        }.attach()

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_info);
        tabLayout.getTabAt(0)?.text = "Info";
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_other);
        tabLayout.getTabAt(1)?.text = "Other";
    }
}
