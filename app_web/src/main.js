// FitLife Web App - Core Logic

// 1. Translations Dictionary
const TRANSLATIONS = {
  ID: {
    welcome_title: "Selamat Datang {name},",
    welcome_question: "Apakah Anda siap\nuntuk latihan?",
    btn_exit: "KELUAR",
    btn_start: "MULAI",
    menu_title: "Menu Latihan",
    running_title: "Berlari",
    running_desc: "Lacak jarak lari, waktu, dan kalori yang terbakar. Tetap termotivasi dengan informasi perkembangan dan target harian.",
    pushup_title: "Push-up",
    pushup_desc: "Hitung push-up Anda, pantau setnya, dan pantau kalori yang terbakar. Bangun kekuatan tubuh bagian atas selangkah demi selangkah.",
    yoga_title: "Yoga",
    yoga_desc: "Ikuti sesi yoga terpandu, tingkatkan fleksibilitas, dan rilekskan pikiran Anda dengan rutinitas yang menenangkan.",
    click_to_start: "Klik di sini untuk mengatur profil Anda",
    health_status_empty: "Mulai dengan mengisi profil",
    health_status_underweight: "Berat Badan Kurang",
    health_status_ideal: "Berat Badan Ideal",
    health_status_overweight: "Kelebihan Berat Badan",
    health_status_obese: "Obesitas",
    user_goal_prefix: "Target: {goal}",
    btn_back: "KEMBALI",
    btn_restart: "MULAI ULANG",
    btn_menu: "MENU",
    btn_continue: "LANJUT",
    btn_save: "Simpan Profil",
    btn_update: "Perbarui Profil",
    fill_all_data: "Harap isi semua kolom!",
    profile_saved: "Profil berhasil disimpan!",
    profile_list_title: "Daftar Profil",
    profile_form_title_new: "Profil Baru",
    profile_form_title_edit: "Edit Profil",
    name_label: "Nama",
    age_label: "Umur (tahun)",
    weight_label: "Berat (kg)",
    height_label: "Tinggi (cm)",
    goal_label: "Target Kebugaran",
    coach_ai_welcome_1: "Halo! Saya Coach AI, asisten kebugaran pribadimu. 💪",
    coach_ai_welcome_2: "Bagaimana saya bisa membantumu hari ini? Silakan pilih topik di bawah ini atau ketik pertanyaanmu.",
    chat_placeholder: "Tanyakan saran fitness...",
    api_key_error: "API Key belum diset atau tidak valid!",
    run_ready: "Siap Lari?",
    run_status: "Sedang Berlari...",
    run_steps: "Langkah",
    run_distance: "Jarak (km)",
    run_calories: "Kalori (kcal)",
    run_simulate_auto: "Simulasi Lari Otomatis",
    run_simulate_manual: "Langkah Manual (Spasi)",
    pushup_ready: "Siap Mulai?",
    pushup_status: "Lakukan Push-up!",
    pushup_sub: "Ketuk area di bawah / Tekan Spasi",
    pushup_zone_idle: "Tekan & Tahan Di Sini\n(Simulasi Dada Turun)",
    pushup_zone_active: "Dada Menyentuh HP!\n(Lepas untuk Hitung)",
    yoga_ready: "Siap Mulai?",
    yoga_status: "Sesi Yoga Terpandu",
    yoga_inhale: "Tarik Napas",
    yoga_exhale: "Hembuskan Napas",
    yoga_pose_1: "Pose Gunung (Tadasana)",
    yoga_pose_2: "Pose Anak (Balasana)",
    yoga_pose_3: "Pose Kobra (Bhujangasana)",
    yoga_pose_4: "Pose Mayat (Savasana)",
    pose_progress: "Pose {current} dari {total}",
    running_instruction_title: "Instruksi Berlari",
    running_instruction_body: "1. Mulai pelacakan langkah dengan tombol Mulai.\n2. Bawa laptop/HP Anda, atau jalankan simulasi otomatis.\n3. Lacak langkah, jarak, dan kalori secara real-time.",
    pushup_instruction_title: "Instruksi Push-Up",
    pushup_instruction_body: "1. Ambil posisi plank di depan laptop/HP.\n2. Lakukan push-up hingga dada mendekati layar.\n3. Tekan spasi atau ketuk zona sensor untuk mensimulasikan repetisi.",
    yoga_instruction_title: "Instruksi Yoga",
    yoga_instruction_body: "1. Ikuti instruksi pose yoga terpandu.\n2. Sesi ini terdiri dari 4 pose (masing-masing 20 detik).\n3. Atur pernapasan Anda mengikuti lingkaran animasi di layar.",
    finished_running_title: "Lari Selesai!",
    finished_running_body: "Luar biasa! Kamu berhasil menyelesaikan latihan lari.\nTotal Langkah: {steps}\nEstimasi Jarak: {distance} km\nSetiap langkah membawamu lebih dekat ke target!",
    finished_pushup_title: "Push-Up Selesai!",
    finished_pushup_body: "Latihan selesai! Selamat! Kamu menyelesaikan sesi push-up dengan kuat.\nTotal Repetisi: {count}",
    finished_yoga_title: "Yoga Selesai!",
    finished_yoga_body: "Latihan selesai! Selamat! Anda menyelesaikan sesi yoga dengan penuh kesadaran dan keseimbangan.",
  },
  EN: {
    welcome_title: "Welcome {name},",
    welcome_question: "Are you ready for\nyour exercise?",
    btn_exit: "EXIT",
    btn_start: "START",
    menu_title: "Exercise Menu",
    running_title: "Running",
    running_desc: "Track your running distance, time, and calories burned. Stay motivated with progress updates and daily goals.",
    pushup_title: "Push-up",
    pushup_desc: "Count your push-ups, monitor sets, and track calories burned. Build upper body strength step by step.",
    yoga_title: "Yoga",
    yoga_desc: "Follow guided yoga sessions, improve flexibility, and relax your mind with calming routines.",
    click_to_start: "Click here to set up your profile",
    health_status_empty: "Fill profile to start",
    health_status_underweight: "Underweight",
    health_status_ideal: "Ideal Weight",
    health_status_overweight: "Overweight",
    health_status_obese: "Obese",
    user_goal_prefix: "Target: {goal}",
    btn_back: "BACK",
    btn_restart: "RESTART",
    btn_menu: "MENU",
    btn_continue: "CONTINUE",
    btn_save: "Save Profile",
    btn_update: "Update Profile",
    fill_all_data: "Please fill all fields!",
    profile_saved: "Profile saved successfully!",
    profile_list_title: "Profile List",
    profile_form_title_new: "New Profile",
    profile_form_title_edit: "Edit Profile",
    name_label: "Name",
    age_label: "Age (years)",
    weight_label: "Weight (kg)",
    height_label: "Height (cm)",
    goal_label: "Fitness Goal",
    coach_ai_welcome_1: "Hello! I am Coach AI, your personal fitness coach. 💪",
    coach_ai_welcome_2: "How can I help you today? Select a topic below or type your question.",
    chat_placeholder: "Ask for fitness advice...",
    api_key_error: "API Key is not set or invalid!",
    run_ready: "Ready to Run?",
    run_status: "Running...",
    run_steps: "Steps",
    run_distance: "Distance (km)",
    run_calories: "Calories (kcal)",
    run_simulate_auto: "Auto Run Simulation",
    run_simulate_manual: "Manual Step (Space)",
    pushup_ready: "Ready?",
    pushup_status: "Do Push-ups!",
    pushup_sub: "Tap area below / Press Spacebar",
    pushup_zone_idle: "Press & Hold Here\n(Simulate Chest Down)",
    pushup_zone_active: "Chest Touched Phone!\n(Release to Count)",
    yoga_ready: "Ready?",
    yoga_status: "Guided Yoga Session",
    yoga_inhale: "Inhale",
    yoga_exhale: "Exhale",
    yoga_pose_1: "Mountain Pose (Tadasana)",
    yoga_pose_2: "Child's Pose (Balasana)",
    yoga_pose_3: "Cobra Pose (Bhujangasana)",
    yoga_pose_4: "Corpse Pose (Savasana)",
    pose_progress: "Pose {current} of {total}",
    running_instruction_title: "Running Instruction",
    running_instruction_body: "1. Start tracking steps with the Start button.\n2. Carry your device or use the auto simulation.\n3. Track steps, distance, and calories real-time.",
    pushup_instruction_title: "Push-Up Instruction",
    pushup_instruction_body: "1. Get in a plank position in front of your screen.\n2. Do push-ups lowering your chest towards the screen.\n3. Press Spacebar or tap the sensor zone to count reps.",
    yoga_instruction_title: "Yoga Instruction",
    yoga_instruction_body: "1. Follow guided yoga pose instructions.\n2. This session has 4 poses (20 seconds each).\n3. Match your breathing to the animating circle.",
    finished_running_title: "Running Finished!",
    finished_running_body: "Awesome! You completed your running session.\nTotal Steps: {steps}\nEstimated Distance: {distance} km\nEvery step brings you closer to your goal!",
    finished_pushup_title: "Push-Up Finished!",
    finished_pushup_body: "Workout finished! Congratulations! You finished your push-up session strong.\nTotal Reps: {count}",
    finished_yoga_title: "Yoga Finished!",
    finished_yoga_body: "Workout finished! Congratulations! You finished your yoga session with mindfulness and balance.",
  }
};

// 1b. Yoga SVG Silhouettes for premium visual representation
const YOGA_SVGs = {
  lotus: `<svg viewBox="0 0 100 100" class="yoga-pose-svg" xmlns="http://www.w3.org/2000/svg">
    <circle cx="50" cy="26" r="6" stroke="white" stroke-width="4" fill="none"/>
    <path d="M50,32 L50,58" stroke="white" stroke-width="4" stroke-linecap="round" fill="none"/>
    <path d="M32,64 C30,50 36,38 50,38 C64,38 70,50 68,64" stroke="white" stroke-width="4" stroke-linecap="round" fill="none"/>
    <path d="M26,64 C26,56 42,56 50,64 C58,56 74,56 74,64 C66,72 34,72 26,64 Z" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
  </svg>`,
  mountain: `<svg viewBox="0 0 100 100" class="yoga-pose-svg" xmlns="http://www.w3.org/2000/svg">
    <circle cx="50" cy="20" r="7" stroke="white" stroke-width="4" fill="none" stroke-linecap="round"/>
    <path d="M50,27 L50,55" stroke="white" stroke-width="4" stroke-linecap="round" fill="none"/>
    <path d="M50,30 C38,34 38,46 50,46 C62,46 62,34 50,30" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
    <path d="M44,90 L48,55 L52,55 L56,90" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
  </svg>`,
  child: `<svg viewBox="0 0 100 100" class="yoga-pose-svg" xmlns="http://www.w3.org/2000/svg">
    <circle cx="32" cy="72" r="6" stroke="white" stroke-width="4" fill="none"/>
    <path d="M42,68 L16,80" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
    <path d="M76,80 L48,80 C48,80 66,74 72,72 C78,70 60,62 42,68" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
  </svg>`,
  cobra: `<svg viewBox="0 0 100 100" class="yoga-pose-svg" xmlns="http://www.w3.org/2000/svg">
    <circle cx="34" cy="36" r="6" stroke="white" stroke-width="4" fill="none"/>
    <path d="M84,80 L58,80 C50,80 44,70 38,48" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
    <path d="M38,48 L32,80" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
  </svg>`,
  corpse: `<svg viewBox="0 0 100 100" class="yoga-pose-svg" xmlns="http://www.w3.org/2000/svg">
    <circle cx="20" cy="70" r="6" stroke="white" stroke-width="4" fill="none"/>
    <path d="M26,75 L84,75" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
    <path d="M34,75 L52,78" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
    <path d="M84,75 L88,68" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
  </svg>`
};


// 2. Groq AI Integration Configuration
const GROQ_CONFIG = {
  apiKey: "YOUR_GROQ_API_KEY",
  apiUrl: "https://api.groq.com/openai/v1/chat/completions",
  model: "llama-3.1-8b-instant"
};

// 3. State Management
let state = {
  language: localStorage.getItem("FitLifeLanguage") || "ID",
  profiles: JSON.parse(localStorage.getItem("FitLifeProfiles_all")) || [],
  activeProfile: JSON.parse(localStorage.getItem("FitLifeProfile_active")) || null,
  viewHistory: [],
  currentWorkout: null // stores running intervals/timers to clean up
};

// Audio Synthesizer for premium sound feedback
function playBeep(frequency = 800, duration = 0.1) {
  try {
    const AudioContext = window.AudioContext || window.webkitAudioContext;
    if (!AudioContext) return;
    const audioCtx = new AudioContext();
    const oscillator = audioCtx.createOscillator();
    const gainNode = audioCtx.createGain();
    
    oscillator.connect(gainNode);
    gainNode.connect(audioCtx.destination);
    
    oscillator.frequency.value = frequency;
    oscillator.type = 'sine';
    
    gainNode.gain.setValueAtTime(0.1, audioCtx.currentTime);
    gainNode.gain.exponentialRampToValueAtTime(0.01, audioCtx.currentTime + duration);
    
    oscillator.start(audioCtx.currentTime);
    oscillator.stop(audioCtx.currentTime + duration);
  } catch (e) {
    console.warn("Audio Context not allowed or supported yet", e);
  }
}

// Mobile-like short vibration
function triggerVibration(duration = 100) {
  if (navigator.vibrate) {
    navigator.vibrate(duration);
  }
}

// 4. View routing
function showView(viewId) {
  // Clean up any running workouts/intervals first
  cleanupCurrentWorkout();

  // Manage view visibility
  document.querySelectorAll(".view").forEach(view => {
    if (view.id === viewId) {
      view.classList.remove("hidden");
      view.classList.add("active");
    } else {
      view.classList.remove("active");
      view.classList.add("hidden");
    }
  });

  // Track history
  state.viewHistory.push(viewId);
  
  // Specific view hooks
  if (viewId === "view-exercise-menu") {
    updateDashboard();
  } else if (viewId === "view-profile-list") {
    renderProfileList();
  } else if (viewId === "view-chatbot") {
    initChatbot();
  } else if (viewId === "view-welcome") {
    updateWelcomeText();
  }
}

function cleanupCurrentWorkout() {
  if (state.currentWorkout) {
    if (state.currentWorkout.timerInterval) clearInterval(state.currentWorkout.timerInterval);
    if (state.currentWorkout.simulationInterval) clearInterval(state.currentWorkout.simulationInterval);
    if (state.currentWorkout.yogaTimer) state.currentWorkout.yogaTimer.cancel();
    state.currentWorkout = null;
  }
  // Remove event listeners or active states
  document.removeEventListener("keydown", handleGlobalKeyDown);
  const clickZone = document.getElementById("pushup-click-zone");
  if (clickZone) {
    clickZone.classList.remove("active");
  }
  document.getElementById("modal-container").classList.add("hidden");
}

// 5. Translations & UI strings updating
function setLanguage(lang) {
  state.language = lang;
  localStorage.setItem("FitLifeLanguage", lang);
  
  // Update flag selection styles
  document.getElementById("lang-id").classList.toggle("active", lang === "ID");
  document.getElementById("lang-en").classList.toggle("active", lang === "EN");
  
  updateGlobalLanguageUI();
}

function updateGlobalLanguageUI() {
  const dict = TRANSLATIONS[state.language];

  // Welcome Screen
  updateWelcomeText();
  document.getElementById("btn-welcome-exit").innerText = dict.btn_exit;
  document.getElementById("btn-welcome-start").innerText = dict.btn_start;

  // Exercise Menu
  document.getElementById("tv-menu-title").innerText = dict.menu_title;
  document.getElementById("tv-running-title").innerText = dict.running_title;
  document.getElementById("tv-running-desc").innerText = dict.running_desc;
  document.getElementById("tv-pushup-title").innerText = dict.pushup_title;
  document.getElementById("tv-pushup-desc").innerText = dict.pushup_desc;
  document.getElementById("tv-yoga-title").innerText = dict.yoga_title;
  document.getElementById("tv-yoga-desc").innerText = dict.yoga_desc;

  // Profile List
  document.getElementById("tv_title_profile_list").innerText = dict.profile_list_title;
  document.getElementById("btn-add-profile").innerText = (state.language === "ID" ? "TAMBAH PROFIL BARU" : "ADD NEW PROFILE");

  // Profile Form Labels
  document.querySelector("label[for='et-name']").innerText = dict.name_label;
  document.querySelector("label[for='et-age']").innerText = dict.age_label;
  document.querySelector("label[for='et-weight']").innerText = dict.weight_label;
  document.querySelector("label[for='et-height']").innerText = dict.height_label;
  document.querySelector("label[for='spinner-goal']").innerText = dict.goal_label;

  // Exercise Running Screen
  document.getElementById("tv_countdown_title_running").innerText = dict.running_title;
  document.getElementById("tv-steps-label").innerText = dict.run_steps;
  document.querySelector(".stats-container-xml .stat-column-xml:nth-child(3) .stat-label-text-xml").innerText = dict.run_distance.split(" ")[0];
  document.querySelector(".sim-toggle-label-text").innerText = dict.run_simulate_auto;
  document.getElementById("btn-step-simulate").innerText = dict.run_simulate_manual;

  // Exercise Push-up Screen
  document.getElementById("tv_countdown_title_pushup").innerText = dict.pushup_title;
  document.getElementById("tv-pushup-sub").innerText = dict.pushup_sub;

  // Exercise Yoga Screen
  document.getElementById("tv_countdown_title_yoga").innerText = dict.yoga_title;
}

function updateWelcomeText() {
  const dict = TRANSLATIONS[state.language];
  const name = state.activeProfile ? state.activeProfile.name : "User";
  document.getElementById("tv-welcome").innerText = dict.welcome_title.replace("{name}", name);
  document.getElementById("tv-question").innerText = dict.welcome_question;
}

// 6. Dashboard & BMI Management
function updateDashboard() {
  const dict = TRANSLATIONS[state.language];
  const bmiValEl = document.getElementById("tv-bmi-value");
  const healthStatusEl = document.getElementById("tv-health-status");
  const userGoalEl = document.getElementById("tv-user-goal");

  if (!state.activeProfile) {
    bmiValEl.innerText = "--";
    healthStatusEl.innerText = dict.health_status_empty;
    userGoalEl.innerText = dict.click_to_start;
    return;
  }

  const weight = parseFloat(state.activeProfile.weight);
  const heightM = parseFloat(state.activeProfile.height) / 100;
  const goal = state.activeProfile.goal;

  if (heightM > 0) {
    const bmi = weight / (heightM * heightM);
    bmiValEl.innerText = bmi.toFixed(1);
    healthStatusEl.innerText = getBmiCategory(bmi);
    userGoalEl.innerText = dict.user_goal_prefix.replace("{goal}", goal);
  } else {
    bmiValEl.innerText = "ERR";
  }
}

function getBmiCategory(bmi) {
  const dict = TRANSLATIONS[state.language];
  if (bmi < 18.5) return dict.health_status_underweight;
  if (bmi < 25) return dict.health_status_ideal;
  if (bmi < 30) return dict.health_status_overweight;
  return dict.health_status_obese;
}

// 7. Profile List Management
function renderProfileList() {
  const container = document.getElementById("profile-list-container");
  container.innerHTML = "";
  
  if (state.profiles.length === 0) {
    container.innerHTML = `<p style="text-align: center; color: var(--text-secondary); margin-top: 40px; font-size: 14px;">
      ${state.language === "ID" ? "Belum ada profil. Klik tombol di bawah untuk menambahkan." : "No profiles yet. Click below to add."}
    </p>`;
    return;
  }

  state.profiles.forEach((profile, index) => {
    const isActive = state.activeProfile && state.activeProfile.name === profile.name;
    const item = document.createElement("div");
    item.className = "xml-profile-card";
    if (isActive) {
      item.style.borderColor = "var(--teal-primary)";
      item.style.borderWidth = "2px";
      item.style.borderStyle = "solid";
    }

    item.innerHTML = `
      <div class="xml-avatar-circle">
        ${profile.name.charAt(0).toUpperCase()}
      </div>
      <div class="xml-profile-details">
        <span class="xml-profile-name">${profile.name} ${isActive ? '✅' : ''}</span>
        <span class="xml-profile-goal">${TRANSLATIONS[state.language].user_goal_prefix.replace("{goal}", profile.goal)}</span>
      </div>
      <button class="xml-btn-edit btn-edit-profile" data-index="${index}">✏️</button>
    `;

    // Click on item selects it
    item.addEventListener("click", (e) => {
      if (e.target.classList.contains("btn-edit-profile")) return;
      selectProfile(profile);
    });

    // Click on edit edits it
    item.querySelector(".btn-edit-profile").addEventListener("click", (e) => {
      e.stopPropagation();
      openProfileForm(profile, index);
    });

    container.appendChild(item);
  });
}

function selectProfile(profile) {
  state.activeProfile = profile;
  localStorage.setItem("FitLifeProfile_active", JSON.stringify(profile));
  showView("view-exercise-menu");
}

let editingIndex = -1; // -1 means creating new profile
function openProfileForm(profile = null, index = -1) {
  editingIndex = index;
  const titleEl = document.getElementById("tv-profile-header");
  const saveBtn = document.getElementById("btn-profile-save");
  const dict = TRANSLATIONS[state.language];

  // Set options language for goal dropdown
  const selectGoal = document.getElementById("spinner-goal");
  selectGoal.innerHTML = "";
  if (state.language === "ID") {
    selectGoal.innerHTML = `
      <option value="Menurunkan Berat Badan">Menurunkan Berat Badan</option>
      <option value="Menjaga Kebugaran">Menjaga Kebugaran</option>
      <option value="Menambah Massa Otot">Menambah Massa Otot</option>
    `;
  } else {
    selectGoal.innerHTML = `
      <option value="Lose Weight">Lose Weight</option>
      <option value="General Fitness">General Fitness</option>
      <option value="Gain Muscle">Gain Muscle</option>
    `;
  }

  if (profile) {
    titleEl.innerText = dict.profile_form_title_edit;
    saveBtn.innerText = dict.btn_update;
    document.getElementById("et-name").value = profile.name;
    document.getElementById("et-age").value = profile.age;
    document.getElementById("et-weight").value = profile.weight;
    document.getElementById("et-height").value = profile.height;
    selectGoal.value = profile.goal;
  } else {
    titleEl.innerText = dict.profile_form_title_new;
    saveBtn.innerText = dict.btn_save;
    document.getElementById("profile-form").reset();
  }

  showView("view-profile-form");
}

function saveProfile(e) {
  e.preventDefault();
  const dict = TRANSLATIONS[state.language];
  const name = document.getElementById("et-name").value.trim();
  const age = document.getElementById("et-age").value.trim();
  const weight = document.getElementById("et-weight").value.trim();
  const height = document.getElementById("et-height").value.trim();
  const goal = document.getElementById("spinner-goal").value;

  if (!name || !age || !weight || !height) {
    alert(dict.fill_all_data);
    return;
  }

  const profile = { name, age, weight, height, goal };

  if (editingIndex >= 0) {
    // Edit existing
    state.profiles[editingIndex] = profile;
  } else {
    // Check if name already exists
    if (state.profiles.some(p => p.name.toLowerCase() === name.toLowerCase())) {
      alert(state.language === "ID" ? "Nama profil sudah digunakan!" : "Profile name already exists!");
      return;
    }
    // Add new
    state.profiles.push(profile);
  }

  localStorage.setItem("FitLifeProfiles_all", JSON.stringify(state.profiles));
  
  // Set as current active profile
  state.activeProfile = profile;
  localStorage.setItem("FitLifeProfile_active", JSON.stringify(profile));

  alert(dict.profile_saved);
  showView("view-exercise-menu");
}

// 8. General Purpose Modal Dialogs
function showModal(title, bodyText, primaryBtnText, onPrimaryClick, secondaryBtnText = "", onSecondaryClick = null) {
  const modalContainer = document.getElementById("modal-container");
  modalContainer.innerHTML = "";

  const modalContent = document.createElement("div");
  modalContent.className = "modal-content";

  let buttonsHtml = `<button class="btn-primary" id="modal-btn-primary">${primaryBtnText}</button>`;
  if (secondaryBtnText) {
    buttonsHtml = `<button class="btn-secondary" id="modal-btn-secondary">${secondaryBtnText}</button>` + buttonsHtml;
  }

  modalContent.innerHTML = `
    <h3 class="modal-title">${title}</h3>
    <div class="modal-body">${bodyText}</div>
    <div class="modal-buttons">${buttonsHtml}</div>
  `;

  modalContainer.appendChild(modalContent);
  modalContainer.classList.remove("hidden");

  document.getElementById("modal-btn-primary").addEventListener("click", () => {
    modalContainer.classList.add("hidden");
    if (onPrimaryClick) onPrimaryClick();
  });

  if (secondaryBtnText && onSecondaryClick) {
    document.getElementById("modal-btn-secondary").addEventListener("click", () => {
      modalContainer.classList.add("hidden");
      onSecondaryClick();
    });
  }
}

// 9. Exercises Implementations

// --- A. Running Exercise ---
function initRunningExercise() {
  const dict = TRANSLATIONS[state.language];
  showView("view-exercise-running");
  
  // Reset fields
  document.getElementById("tv-running-timer").innerText = "00:00";
  document.getElementById("tv-steps-count").innerText = "0";
  document.getElementById("tv-distance-value").innerText = "0.00";
  document.getElementById("tv-running-label").innerText = dict.run_ready;
  document.getElementById("circle-running-bg").className = "circle-timer-bg green-glow";

  document.getElementById("btn-running-start").classList.remove("hidden");
  document.getElementById("btn-running-stop").classList.add("hidden");
  document.getElementById("btn-running-menu").classList.add("hidden");
  document.getElementById("toggle-run-simulation").checked = false;

  // Show instructions
  showModal(
    dict.running_instruction_title,
    dict.running_instruction_body,
    state.language === "ID" ? "Mulai" : "Start",
    startRunningTracking,
    state.language === "ID" ? "Kembali" : "Back",
    () => showView("view-exercise-menu")
  );
}

function startRunningTracking() {
  const dict = TRANSLATIONS[state.language];
  document.getElementById("btn-running-start").classList.add("hidden");
  document.getElementById("btn-running-stop").classList.remove("hidden");
  document.getElementById("btn-running-menu").classList.remove("hidden");
  document.getElementById("tv-running-label").innerText = dict.run_status;

  playBeep(600, 0.2);

  state.currentWorkout = {
    steps: 0,
    startTime: Date.now(),
    timerInterval: null,
    simulationInterval: null
  };

  // Timer Interval
  state.currentWorkout.timerInterval = setInterval(() => {
    if (!state.currentWorkout) return;
    const elapsed = Date.now() - state.currentWorkout.startTime;
    const seconds = Math.floor(elapsed / 1000) % 60;
    const minutes = Math.floor(elapsed / 60000);
    document.getElementById("tv-running-timer").innerText = 
      `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
  }, 500);

  // Setup simulation listeners
  document.getElementById("toggle-run-simulation").addEventListener("change", handleRunningSimulationToggle);
  document.getElementById("btn-step-simulate").addEventListener("click", incrementRunningSteps);
  document.addEventListener("keydown", handleGlobalKeyDown);
}

function handleRunningSimulationToggle(e) {
  if (!state.currentWorkout) return;
  
  if (e.target.checked) {
    // Start auto steps simulation (e.g. 2.5 steps per second)
    state.currentWorkout.simulationInterval = setInterval(() => {
      incrementRunningSteps();
    }, 400);
  } else {
    if (state.currentWorkout.simulationInterval) {
      clearInterval(state.currentWorkout.simulationInterval);
      state.currentWorkout.simulationInterval = null;
    }
  }
}

function incrementRunningSteps() {
  if (!state.currentWorkout) return;
  state.currentWorkout.steps++;
  
  const distanceKm = state.currentWorkout.steps * 0.00075;
  document.getElementById("tv-steps-count").innerText = state.currentWorkout.steps;
  document.getElementById("tv-distance-value").innerText = distanceKm.toFixed(2);
  
  // Soft audio sound when steps simulated manually
  triggerVibration(50);
}

function stopRunningTracking() {
  if (!state.currentWorkout) return;
  
  const finalSteps = state.currentWorkout.steps;
  const finalDistance = (finalSteps * 0.00075).toFixed(2);

  playBeep(400, 0.4);
  cleanupCurrentWorkout();

  // Show workout finished dialog
  const dict = TRANSLATIONS[state.language];
  const finishedBodyText = dict.finished_running_body
    .replace("{steps}", finalSteps)
    .replace("{distance}", finalDistance);

  showModal(
    dict.finished_running_title,
    finishedBodyText,
    dict.btn_restart,
    initRunningExercise,
    dict.btn_menu,
    () => showView("view-exercise-menu")
  );
}


// --- B. Push-up Exercise ---
function initPushupExercise() {
  const dict = TRANSLATIONS[state.language];
  showView("view-exercise-pushup");
  
  // Reset
  document.getElementById("tv-pushup-count").innerText = "0";
  document.getElementById("tv-pushup-label").innerText = dict.pushup_ready;
  document.getElementById("circle-pushup-bg").className = "circle-timer-bg green-glow";
  
  document.getElementById("btn-pushup-start").classList.remove("hidden");
  document.getElementById("btn-pushup-restart").classList.add("hidden");
  document.getElementById("btn-pushup-menu").classList.add("hidden");

  // Instructions
  showModal(
    dict.pushup_instruction_title,
    dict.pushup_instruction_body,
    state.language === "ID" ? "Mulai" : "Start",
    startPushupTracking,
    state.language === "ID" ? "Kembali" : "Back",
    () => showView("view-exercise-menu")
  );
}

function startPushupTracking() {
  const dict = TRANSLATIONS[state.language];
  document.getElementById("btn-pushup-start").classList.add("hidden");
  document.getElementById("btn-pushup-restart").classList.remove("hidden");
  document.getElementById("btn-pushup-menu").classList.remove("hidden");
  document.getElementById("tv-pushup-label").innerText = dict.pushup_status;

  playBeep(600, 0.2);

  state.currentWorkout = {
    count: 0,
    isDown: false
  };

  // Click zone setup
  const clickZone = document.getElementById("pushup-click-zone");
  
  clickZone.addEventListener("mousedown", handlePushupDown);
  clickZone.addEventListener("mouseup", handlePushupUp);
  clickZone.addEventListener("mouseleave", handlePushupLeave);
  
  clickZone.addEventListener("touchstart", (e) => { e.preventDefault(); handlePushupDown(); });
  clickZone.addEventListener("touchend", (e) => { e.preventDefault(); handlePushupUp(); });

  document.addEventListener("keydown", handleGlobalKeyDown);
  document.addEventListener("keyup", handleGlobalKeyUp);
}

function handlePushupDown() {
  if (!state.currentWorkout || state.currentWorkout.isDown) return;
  state.currentWorkout.isDown = true;
  
  const zoneText = document.getElementById("tv-pushup-zone-text");
  const dict = TRANSLATIONS[state.language];
  zoneText.innerHTML = dict.pushup_zone_active;
  document.getElementById("pushup-click-zone").classList.add("active");
  
  triggerVibration(60);
}

function handlePushupUp() {
  if (!state.currentWorkout || !state.currentWorkout.isDown) return;
  state.currentWorkout.isDown = false;
  
  const zoneText = document.getElementById("tv-pushup-zone-text");
  const dict = TRANSLATIONS[state.language];
  zoneText.innerHTML = dict.pushup_zone_idle;
  document.getElementById("pushup-click-zone").classList.remove("active");

  state.currentWorkout.count++;
  document.getElementById("tv-pushup-count").innerText = state.currentWorkout.count;
  
  // Successful repetition sound feedback
  playBeep(880, 0.1);
  triggerVibration(100);
}

function handlePushupLeave() {
  if (!state.currentWorkout || !state.currentWorkout.isDown) return;
  // Cancel pushup rep if mouse slips off without release
  state.currentWorkout.isDown = false;
  const zoneText = document.getElementById("tv-pushup-zone-text");
  const dict = TRANSLATIONS[state.language];
  zoneText.innerHTML = dict.pushup_zone_idle;
  document.getElementById("pushup-click-zone").classList.remove("active");
}

function stopPushupTracking() {
  if (!state.currentWorkout) return;
  
  const finalCount = state.currentWorkout.count;
  cleanupCurrentWorkout();

  playBeep(400, 0.4);

  const dict = TRANSLATIONS[state.language];
  showModal(
    dict.finished_pushup_title,
    dict.finished_pushup_body.replace("{count}", finalCount),
    dict.btn_restart,
    initPushupExercise,
    dict.btn_menu,
    () => showView("view-exercise-menu")
  );
}


// --- C. Yoga Exercise ---
function initYogaExercise() {
  const dict = TRANSLATIONS[state.language];
  showView("view-exercise-yoga");
  
  // Reset
  document.getElementById("tv-yoga-timer").innerText = "00";
  document.getElementById("tv-yoga-pose-name").innerText = dict.yoga_ready;
  document.getElementById("tv-yoga-pose-progress").innerText = dict.yoga_status;
  document.getElementById("tv-yoga-breathing").innerText = "";
  document.getElementById("tv-yoga-silhouette").innerHTML = YOGA_SVGs.lotus;
  document.getElementById("breathing-ring").className = "breathing-ring";

  document.getElementById("btn-yoga-start").classList.remove("hidden");
  document.getElementById("btn-yoga-restart").classList.add("hidden");
  document.getElementById("btn-yoga-menu").classList.add("hidden");

  // Instructions
  showModal(
    dict.yoga_instruction_title,
    dict.yoga_instruction_body,
    state.language === "ID" ? "Mulai" : "Start",
    startYogaTracking,
    state.language === "ID" ? "Kembali" : "Back",
    () => showView("view-exercise-menu")
  );
}

function startYogaTracking() {
  document.getElementById("btn-yoga-start").classList.add("hidden");
  document.getElementById("btn-yoga-restart").classList.remove("hidden");
  document.getElementById("btn-yoga-menu").classList.remove("hidden");

  playBeep(600, 0.2);

  state.currentWorkout = {
    poseIndex: 0,
    poseDuration: 20000, // 20s
    poseTimeLeft: 20,
    timerInterval: null,
    breathingCycle: 0, // odd: Inhale, even: Exhale
    breathingInterval: null,
    poses: [
      { nameKey: "yoga_pose_1", svg: YOGA_SVGs.mountain }, // Mountain Pose
      { nameKey: "yoga_pose_2", svg: YOGA_SVGs.child },    // Child's Pose
      { nameKey: "yoga_pose_3", svg: YOGA_SVGs.cobra },    // Cobra Pose
      { nameKey: "yoga_pose_4", svg: YOGA_SVGs.corpse }    // Corpse Pose
    ]
  };

  startPose(0);
  startBreathingGuide();
}

function startPose(index) {
  if (!state.currentWorkout) return;
  const dict = TRANSLATIONS[state.language];

  if (index >= state.currentWorkout.poses.length) {
    stopYogaTrackingComplete();
    return;
  }

  state.currentWorkout.poseIndex = index;
  state.currentWorkout.poseTimeLeft = 20;

  const currentPose = state.currentWorkout.poses[index];
  document.getElementById("tv-yoga-pose-name").innerText = dict[currentPose.nameKey];
  document.getElementById("tv-yoga-pose-progress").innerText = dict.pose_progress
    .replace("{current}", index + 1)
    .replace("{total}", state.currentWorkout.poses.length);
  
  document.getElementById("tv-yoga-silhouette").innerHTML = currentPose.svg;
  document.getElementById("tv-yoga-timer").innerText = String(state.currentWorkout.poseTimeLeft).padStart(2, '0');

  playBeep(523.25, 0.3); // Play C5 note for new pose
  triggerVibration(200);

  if (state.currentWorkout.timerInterval) clearInterval(state.currentWorkout.timerInterval);

  state.currentWorkout.timerInterval = setInterval(() => {
    if (!state.currentWorkout) return;
    state.currentWorkout.poseTimeLeft--;
    document.getElementById("tv-yoga-timer").innerText = String(state.currentWorkout.poseTimeLeft).padStart(2, '0');

    if (state.currentWorkout.poseTimeLeft <= 0) {
      clearInterval(state.currentWorkout.timerInterval);
      startPose(state.currentWorkout.poseIndex + 1);
    }
  }, 1000);
}

function startBreathingGuide() {
  if (!state.currentWorkout) return;
  const dict = TRANSLATIONS[state.language];
  const ring = document.getElementById("breathing-ring");
  const breathingText = document.getElementById("tv-yoga-breathing");

  // 4 seconds inhale, 4 seconds exhale
  state.currentWorkout.breathingCycle = 0;
  
  const runBreathingCycle = () => {
    if (!state.currentWorkout) return;
    const isInhale = state.currentWorkout.breathingCycle % 2 === 0;
    
    breathingText.innerText = isInhale ? dict.yoga_inhale : dict.yoga_exhale;
    
    if (isInhale) {
      ring.className = "breathing-ring breathing-inhale";
      ring.style.animationPlayState = "running";
    } else {
      ring.className = "breathing-ring";
      ring.style.animation = "none";
      // force reflow
      void ring.offsetWidth;
      ring.style.animation = "breathing-ring-inhale 4s infinite ease-in-out reverse";
    }

    state.currentWorkout.breathingCycle++;
  };

  runBreathingCycle();
  state.currentWorkout.breathingInterval = setInterval(runBreathingCycle, 4000);
}

function stopYogaTrackingComplete() {
  cleanupCurrentWorkout();
  playBeep(880, 0.5);

  const dict = TRANSLATIONS[state.language];
  showModal(
    dict.finished_yoga_title,
    dict.finished_yoga_body,
    dict.btn_restart,
    initYogaExercise,
    dict.btn_menu,
    () => showView("view-exercise-menu")
  );
}


// --- Global Key Handlers for keyboard navigation/simulations ---
function handleGlobalKeyDown(e) {
  if (e.code === "Space") {
    e.preventDefault(); // prevent scrolling page down with space
    
    const activeView = document.querySelector(".view.active");
    if (activeView.id === "view-exercise-running") {
      incrementRunningSteps();
    } else if (activeView.id === "view-exercise-pushup") {
      handlePushupDown();
    }
  }
}

function handleGlobalKeyUp(e) {
  if (e.code === "Space") {
    e.preventDefault();
    const activeView = document.querySelector(".view.active");
    if (activeView.id === "view-exercise-pushup") {
      handlePushupUp();
    }
  }
}


// 10. Coach AI (Chatbot) Integration
let chatSessionHistory = [];

function initChatbot() {
  const dict = TRANSLATIONS[state.language];
  const messagesContainer = document.getElementById("chat-messages");
  
  // Clear chat if new session
  messagesContainer.innerHTML = "";
  chatSessionHistory = [];

  // Add initial bot greeting messages
  addBotMessage(dict.coach_ai_welcome_1);
  addBotMessage(dict.coach_ai_welcome_2);

  // Show quick questions chips
  document.getElementById("layout-quick-questions").classList.remove("hidden");
  
  // Setup quick questions chips event listener once
  document.querySelectorAll(".quick-chip-xml").forEach(chip => {
    chip.replaceWith(chip.cloneNode(true)); // remove old listeners
  });
  
  document.querySelectorAll(".quick-chip-xml").forEach(chip => {
    chip.addEventListener("click", () => {
      const question = chip.getAttribute("data-question");
      sendUserChatMessage(question);
    });
  });
}

function parseMarkdown(text) {
  const lines = text.split("\n");
  let insideList = false;
  let listType = ""; // "ul" or "ol"
  let parsedHtml = "";

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line) {
      if (insideList) {
        parsedHtml += `</${listType}>`;
        insideList = false;
        listType = "";
      }
      parsedHtml += "<div class='chat-space'></div>";
      continue;
    }

    // Escape HTML to prevent injection
    let safeLine = line
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;");

    // Replace bold (**text** or __text__)
    safeLine = safeLine.replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>");
    safeLine = safeLine.replace(/__(.*?)__/g, "<strong>$1</strong>");

    // Replace italic (*text* or _text_)
    safeLine = safeLine.replace(/\*(.*?)\*/g, "<em>$1</em>");
    safeLine = safeLine.replace(/_(.*?)_/g, "<em>$1</em>");

    // Headers
    if (safeLine.startsWith("### ")) {
      if (insideList) { parsedHtml += `</${listType}>`; insideList = false; }
      parsedHtml += `<h3>${safeLine.substring(4)}</h3>`;
    } else if (safeLine.startsWith("## ")) {
      if (insideList) { parsedHtml += `</${listType}>`; insideList = false; }
      parsedHtml += `<h2>${safeLine.substring(3)}</h2>`;
    } else if (safeLine.startsWith("# ")) {
      if (insideList) { parsedHtml += `</${listType}>`; insideList = false; }
      parsedHtml += `<h1>${safeLine.substring(2)}</h1>`;
    }
    // Bullet lists
    else if (safeLine.match(/^[-*•]\s+/)) {
      if (!insideList || listType !== "ul") {
        if (insideList) { parsedHtml += `</${listType}>`; }
        parsedHtml += "<ul>";
        insideList = true;
        listType = "ul";
      }
      const content = safeLine.replace(/^[-*•]\s+/, "");
      parsedHtml += `<li>${content}</li>`;
    }
    // Numbered lists
    else if (safeLine.match(/^\d+\.\s+/)) {
      if (!insideList || listType !== "ol") {
        if (insideList) { parsedHtml += `</${listType}>`; }
        parsedHtml += "<ol>";
        insideList = true;
        listType = "ol";
      }
      const content = safeLine.replace(/^\d+\.\s+/, "");
      parsedHtml += `<li>${content}</li>`;
    }
    // Regular paragraph line
    else {
      if (insideList) {
        parsedHtml += `</${listType}>`;
        insideList = false;
        listType = "";
      }
      parsedHtml += `<p>${safeLine}</p>`;
    }
  }
  if (insideList) {
    parsedHtml += `</${listType}>`;
  }
  return parsedHtml;
}

function addBotMessage(text) {
  const messagesContainer = document.getElementById("chat-messages");
  const bubble = document.createElement("div");
  bubble.className = "xml-bubble xml-bot";
  bubble.innerHTML = parseMarkdown(text);
  messagesContainer.appendChild(bubble);
  scrollToBottom(messagesContainer);
}

function addUserMessage(text) {
  const messagesContainer = document.getElementById("chat-messages");
  const bubble = document.createElement("div");
  bubble.className = "xml-bubble xml-user";
  bubble.innerText = text;
  messagesContainer.appendChild(bubble);
  scrollToBottom(messagesContainer);
}

function scrollToBottom(container) {
  container.scrollTop = container.scrollHeight;
}

function getSystemPrompt() {
  const name = state.activeProfile ? state.activeProfile.name : "User";
  const age = state.activeProfile ? state.activeProfile.age : "-";
  const weight = state.activeProfile ? state.activeProfile.weight : "-";
  const height = state.activeProfile ? state.activeProfile.height : "-";
  const goal = state.activeProfile ? state.activeProfile.goal : "Kebugaran Umum";

  return `Kamu adalah FitLife AI Coach, asisten fitness pribadi yang ramah. ` +
         `Selalu jawab dalam Bahasa Indonesia. Berikan saran latihan, nutrisi, dan motivasi yang helpful dan spesifik. ` +
         `Konteks Pengguna saat ini: ` +
         `Nama: ${name}, ` +
         `Umur: ${age} tahun, ` +
         `Berat: ${weight} kg, ` +
         `Tinggi: ${height} cm, ` +
         `Target Fitness: ${goal}. ` +
         `Gunakan data ini untuk memberikan jawaban yang dipersonalisasi. Sapa pengguna dengan namanya sesekali. ` +
         `PENTING: Gunakan format yang rapi. Gunakan baris baru (newline) untuk setiap poin atau langkah. ` +
         `Jangan menulis poin-poin dalam satu paragraf panjang.`;
}

function formatResponse(response) {
  return response.trim();
}

async function sendUserChatMessage(text) {
  if (!text.trim()) return;

  // Render user message
  addUserMessage(text);

  // Hide quick questions panel once chat starts
  document.getElementById("layout-quick-questions").classList.add("hidden");

  // Show loading indicator
  const messagesContainer = document.getElementById("chat-messages");
  const loadingBubble = document.createElement("div");
  loadingBubble.className = "xml-bubble xml-bot bubble-loading";
  loadingBubble.innerHTML = `
    <span class="dot-pulse"></span>
    <span class="dot-pulse"></span>
    <span class="dot-pulse"></span>
  `;
  messagesContainer.appendChild(loadingBubble);
  scrollToBottom(messagesContainer);

  // Assemble system and history context messages
  const apiMessages = [
    { role: "system", content: getSystemPrompt() },
    ...chatSessionHistory,
    { role: "user", content: text }
  ];

  try {
    const response = await fetch(GROQ_CONFIG.apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${GROQ_CONFIG.apiKey}`
      },
      body: JSON.stringify({
        model: GROQ_CONFIG.model,
        messages: apiMessages,
        max_tokens: 1024,
        temperature: 0.7
      })
    });

    // Remove loading bubble
    loadingBubble.remove();

    if (!response.ok) {
      const errData = await response.text();
      console.error("Groq API error details:", errData);
      throw new Error(`API Error: ${response.status}`);
    }

    const data = await response.json();
    const rawBotResponse = data.choices[0].message.content;
    const formattedBotResponse = formatResponse(rawBotResponse);

    // Save in session history
    chatSessionHistory.push({ role: "user", content: text });
    chatSessionHistory.push({ role: "assistant", content: rawBotResponse });

    // Render response
    addBotMessage(formattedBotResponse);

  } catch (error) {
    loadingBubble.remove();
    addBotMessage(TRANSLATIONS[state.language].api_key_error + " (" + error.message + ")");
    console.error("Failed calling Groq AI:", error);
  }
}


// 11. Initial Application Bindings
function initApp() {
  // Translate initial static UI elements
  updateGlobalLanguageUI();

  // SPLASH SCREEN Listeners
  document.getElementById("lang-id").addEventListener("click", () => setLanguage("ID"));
  document.getElementById("lang-en").addEventListener("click", () => setLanguage("EN"));

  document.getElementById("btn-splash-continue").addEventListener("click", () => {
    // If profiles exist, go to welcome screen, else go straight to profile form
    if (state.profiles.length > 0) {
      if (!state.activeProfile) {
        state.activeProfile = state.profiles[0];
        localStorage.setItem("FitLifeProfile_active", JSON.stringify(state.profiles[0]));
      }
      showView("view-welcome");
    } else {
      openProfileForm(null, -1);
    }
  });

  // WELCOME SCREEN Listeners
  document.getElementById("btn-welcome-exit").addEventListener("click", () => {
    showView("view-splash");
  });

  document.getElementById("btn-welcome-start").addEventListener("click", () => {
    showView("view-exercise-menu");
  });

  // EXERCISE MENU Listeners
  document.getElementById("btn-menu-back").addEventListener("click", () => {
    showView("view-welcome");
  });

  document.getElementById("dashboard-card").addEventListener("click", () => {
    showView("view-profile-list");
  });

  // Exercise navigators
  document.getElementById("card-running").addEventListener("click", initRunningExercise);
  document.getElementById("card-pushup").addEventListener("click", initPushupExercise);
  document.getElementById("card-yoga").addEventListener("click", initYogaExercise);

  // FAB Coach AI listener
  const handleFabChatbotClick = () => {
    if (!state.activeProfile) {
      showView("view-profile-list");
    } else {
      showView("view-chatbot");
    }
  };

  document.getElementById("fab-coach-ai").addEventListener("click", handleFabChatbotClick);

  // Exercise screen FAB buttons
  const fabRunChat = document.getElementById("fab-running-chatbot");
  if (fabRunChat) fabRunChat.addEventListener("click", handleFabChatbotClick);

  const fabPushChat = document.getElementById("fab-pushup-chatbot");
  if (fabPushChat) fabPushChat.addEventListener("click", handleFabChatbotClick);

  const fabYogaChat = document.getElementById("fab-yoga-chatbot");
  if (fabYogaChat) fabYogaChat.addEventListener("click", handleFabChatbotClick);

  // PROFILE LIST Listeners
  document.getElementById("btn-profile-list-back").addEventListener("click", () => {
    showView("view-exercise-menu");
  });

  document.getElementById("btn-add-profile").addEventListener("click", () => {
    openProfileForm(null, -1);
  });

  // PROFILE FORM Listeners
  document.getElementById("btn-profile-form-back").addEventListener("click", () => {
    showView("view-profile-list");
  });

  document.getElementById("profile-form").addEventListener("submit", saveProfile);

  // CHATBOT SCREEN Listeners
  document.getElementById("btn-chat-close").addEventListener("click", () => {
    showView("view-exercise-menu");
  });

  const sendChatMsg = () => {
    const input = document.getElementById("et-message");
    const msg = input.value;
    input.value = "";
    sendUserChatMessage(msg);
  };

  document.getElementById("btn-send").addEventListener("click", sendChatMsg);
  document.getElementById("et-message").addEventListener("keypress", (e) => {
    if (e.key === "Enter") sendChatMsg();
  });

  // EXERCISES Back/Stop/Menu Click Bindings
  document.getElementById("btn-running-back").addEventListener("click", () => showView("view-exercise-menu"));
  document.getElementById("btn-running-stop").addEventListener("click", stopRunningTracking);
  document.getElementById("btn-running-menu").addEventListener("click", () => showView("view-exercise-menu"));

  document.getElementById("btn-pushup-back").addEventListener("click", () => showView("view-exercise-menu"));
  document.getElementById("btn-pushup-restart").addEventListener("click", initPushupExercise);
  document.getElementById("btn-pushup-menu").addEventListener("click", stopPushupTracking);

  document.getElementById("btn-yoga-back").addEventListener("click", () => showView("view-exercise-menu"));
  document.getElementById("btn-yoga-restart").addEventListener("click", initYogaExercise);
  document.getElementById("btn-yoga-menu").addEventListener("click", () => {
    cleanupCurrentWorkout();
    showView("view-exercise-menu");
  });
}

if (document.readyState === "loading") {
  document.addEventListener("DOMContentLoaded", initApp);
} else {
  initApp();
}
